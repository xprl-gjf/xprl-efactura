package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.NotaCredito
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a NotaCredito.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class NotaCredito(
    src: NotaCredito
) : ComprobanteElectronico(src) {

    val comprador: Comprador = Comprador(src.comprador)
    val credito: Credito = Credito(src.credito)
    val detalles: List<ComprobanteDetalle> = src.detalles.map { ComprobanteDetalle(it) }

    class Credito(val src: NotaCredito.Credito) {
        val codDocModificado: String
            get() = String.format("%02d", src.documentoModificado.tipoDocumento.value)
        val numDocModificado: String
            get() = src.documentoModificado.id.toString()
        val fechaEmisionDocSustento: String
            get() = src.documentoModificado.fechaEmisionDocSustento.toDateString()
        val motivo: String
            get() = src.motivo.value
        val rise: String?
            get() = src.rise?.value

        val valores: Valores = Valores(src.valores)
    }

    class Valores(val src: NotaCredito.Valores) {
        val totalSinImpuestos: BigDecimal
            get() = src.totalSinImpuestos.toBigDecimal()
        val valorModificacion: BigDecimal
            get() = src.valorModificacion.toBigDecimal()
        val moneda: String
            get() = src.moneda.value

        val totalConImpuestos: List<Impuesto> = src.totalConImpuestos.map { (k, v) ->
            Impuesto(k, v)
        }
    }
}
