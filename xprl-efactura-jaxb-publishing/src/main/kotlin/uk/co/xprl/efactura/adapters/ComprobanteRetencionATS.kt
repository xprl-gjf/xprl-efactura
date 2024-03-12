package uk.co.xprl.efactura.adapters

import kotlinx.datetime.LocalDate
import uk.co.xprl.efactura.ComprobanteRetencionATS

/**
 * Immutable formatted data representation of a ComprobanteRetencionATS (schema v2.0.0).
 */
@Suppress("MemberVisibilityCanBePrivate")
class ComprobanteRetencionATS(
    src: ComprobanteRetencionATS
) : ComprobanteElectronico(src) {

    val sujeto: Sujeto = Sujeto(src.sujeto)
    val valores: Valores = Valores(src.valores)

    class Sujeto(val src: ComprobanteRetencionATS.Sujeto) {
        val tipoIdentificación: String
            get() = String.format("%02d", src.identificación.tipoIdentificacionCodigo)
        val tipoSujetoRetenido: String?
            get() = src.tipoSujetoRetenido?.let {String.format("%02d", it) }
        val identificación: String
            get() = src.identificación.value
        val razónSocial: String
            get() = src.razónSocial.value
        val parteRelacionado: Boolean
            get() = src.parteRelacionada
    }

    class Valores(val src: ComprobanteRetencionATS.Valores) {
        val periodoFiscal: String
            get() = src.periodoFiscal.toPeriodoFiscalDateString()
        val sustentos: List<SustentoRetencion> = src.sustentos.map { SustentoRetencion(it) }
    }
}

private fun LocalDate.toPeriodoFiscalDateString() = toMonthAndYearDateString()
