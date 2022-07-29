package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.Factura
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a Factura.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class Factura(
    src: Factura
) : ComprobanteElectronico(src) {

    val comprador: Comprador = Comprador(src.comprador)
    val valores: Valores = Valores(src.valores)
    val detalles: List<ComprobanteDetalle> = src.detalles.map { ComprobanteDetalle(it) }


    internal class Valores(val src: Factura.Valores) {
        val totals: Totals = Totals(src.totals)
        val pagos: List<Pago> = src.pagos.map { Pago(it) }

        val propina: BigDecimal
            get() = src.propina?.toBigDecimal() ?: BigDecimal(0)
        val moneda: String
            get() = src.moneda.toString()
        val retIva: BigDecimal?
            get() = src.retIva?.toBigDecimal()
        val retRenta: BigDecimal?
            get() = src.retRenta?.toBigDecimal()
    }


    internal class Totals(val src: Factura.Totals) {
        val totalConImpuestos: List<Impuesto> = src.totalConImpuestos.map { (k, v) ->
            Impuesto(k, v)
        }
        val totalSinImpuestos: BigDecimal
            get() = src.totalSinImpuestos.toBigDecimal()
        val totalDescuento: BigDecimal
            get() = src.totalDescuento.toBigDecimal()
        val importeTotal: BigDecimal
            get() = src.importeTotal.toBigDecimal()
    }
}

