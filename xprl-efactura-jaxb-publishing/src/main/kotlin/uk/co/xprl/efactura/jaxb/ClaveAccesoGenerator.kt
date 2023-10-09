package uk.co.xprl.efactura.jaxb

import uk.co.xprl.efactura.*
import ec.gob.sri.efactura.DocumentType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

typealias ClaveAcceso = CharSequence
typealias Serie = UInt
typealias CodigoNumerico = UInt

/**
 * Interface for any object that can generate a [ClaveAcceso].
 */
interface ClaveAccesoGenerator {
    /**
     * Generate a [ClaveAcceso] for a comprobante electrónico that is being
     * published for a given ambiente.
     */
    fun genClaveAcceso(comprobante: ComprobanteElectronico, ambiente: Ambiente, tipoEmision: TipoEmisión): ClaveAcceso
}

sealed class ClaveAccesoGeneratorImpl(
    val genClaveValores: (ComprobanteElectronico, Ambiente, TipoEmisión) -> Pair<Serie, CodigoNumerico>
) : ClaveAccesoGenerator {

    object Default: ClaveAccesoGeneratorImpl(::genDefaultClaveValores)

    override fun genClaveAcceso(comprobante: ComprobanteElectronico, ambiente: Ambiente, tipoEmision: TipoEmisión): ClaveAcceso {
        val (serie, codigoNumerico) = genClaveValores(comprobante, ambiente, tipoEmision)
        return Companion.genClaveAcceso(comprobante, ambiente, tipoEmision, serie, codigoNumerico)
    }

    companion object {
        @JvmStatic
        fun genClaveAcceso(
            comprobante: ComprobanteElectronico,
            ambiente: Ambiente,
            tipoEmision: TipoEmisión,
            serie: Serie,
            codigoNumerico: CodigoNumerico
        ) = genClaveAcceso(
            comprobante.fechaEmision,
            comprobante.emisor.RUC,
            comprobante.secuencial,
            comprobante.sriDocumentType,
            ambiente,
            serie,
            codigoNumerico,
            tipoEmision
        )
    }
}

@Suppress("UNUSED_PARAMETER")
private fun genDefaultClaveValores(
    comprobante: ComprobanteElectronico,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión
): Pair<Serie, CodigoNumerico> {
    val serie = genDefaultSerie(comprobante)
    val codigo = genDefaultCodigoNumerico()
    return (Pair(serie, codigo))
}

private fun genDefaultSerie(comprobante: ComprobanteElectronico): Serie = with(comprobante.emisor) {
    (códigoEstablecimiento.value * 1000 + códigoPuntoEmisión.value).toUInt()
}

private val random = kotlin.random.Random.Default

private fun genDefaultCodigoNumerico(): CodigoNumerico =
    random.nextBytes(4)
        .fold(0) { acc, byte -> acc * 256 + byte }
        .toUInt() % 100000000U      // limit to 8 digits

private fun genClaveAcceso(
    fechaEmision: LocalDate,
    RUC: IdentityValue.RUC,
    numeroComprobante: SecuencialValue,
    tipoComprobante: DocumentType,
    ambiente: Ambiente,
    serie: Serie,
    codigoNumerico: CodigoNumerico,
    tipoEmision: TipoEmisión,
): ClaveAcceso {
    val builder = StringBuilder(49)
        .append(fechaEmision.toClaveDateString())
        .append(String.format("%02d", tipoComprobante.value))
        .append(RUC)
        .append(ambiente.ambienteCodigo)
        .append(String.format("%06d", serie.toLong()))
        .append(String.format("%09d", numeroComprobante.value))
        .append(String.format("%08d", codigoNumerico.toLong()))
        .append(tipoEmision.tipoEmisionCodigo)

    val checkSum: Int = modulo11(builder.toString())
    return builder.append(checkSum).toString()
}

private val claveAccesoDateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy")
    .withZone(ZoneId.systemDefault())

private fun LocalDate.toClaveDateString(): String =  claveAccesoDateFormatter.format(this.toJavaLocalDate())
