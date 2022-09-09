package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.LiquidacionCompra
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a LiquidacionCompra.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class LiquidacionCompra(
    src: LiquidacionCompra
) : ComprobanteElectronico(src) {

    val proveedor: Proveedor = Proveedor(src.proveedor)
    val valores: Valores = Valores(src.valores)
    val reembolso: Reembolso? = src.reembolso?.let { Reembolso(it) }
    val detalles: List<ComprobanteDetalle> = src.detalles.map { ComprobanteDetalle(it) }
    val reembolsoDetalles: List<ReembolsoDetalle>? = src.reembolsoDetalles?.map { ReembolsoDetalle(it) }


    internal class Valores(val src: LiquidacionCompra.Valores) {
        val totals: Totals = Totals(src.totals)
        val pagos: List<Pago> = src.pagos.map { Pago(it) }

        val moneda: String
            get() = src.moneda.toString()
    }


    internal class Totals(val src: LiquidacionCompra.Totals) {
        val totalConImpuestos: List<ImpuestoLiquidacion> = src.totalConImpuestos.map { (k, v) ->
            ImpuestoLiquidacion(k, v)
        }
        val totalSinImpuestos: BigDecimal
            get() = src.totalSinImpuestos.toBigDecimal()
        val totalDescuento: BigDecimal
            get() = src.totalDescuento.toBigDecimal()
        val importeTotal: BigDecimal
            get() = src.importeTotal.toBigDecimal()
    }


    internal class Reembolso(val src: LiquidacionCompra.Reembolso) {
        val totals: ReembolsoTotals = ReembolsoTotals(src.totals)

        val codDocReembolso: String
            get() = String.format("%02d", src.codDocReembolso.value)
    }

    internal class ReembolsoTotals(val src: LiquidacionCompra.ReembolsoTotals) {
        val totalComprobantesReembolso: BigDecimal
            get() = src.totalComprobantesReembolso.toBigDecimal()
        val totalBaseImponibleReembolso: BigDecimal
            get() = src.totalBaseImponibleReembolso.toBigDecimal()
        val totalImpuestoReembolso: BigDecimal
            get() = src.totalImpuestoReembolso.toBigDecimal()
    }
}
