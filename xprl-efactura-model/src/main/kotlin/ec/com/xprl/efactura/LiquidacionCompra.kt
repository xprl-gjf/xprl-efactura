package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

/**
 * Immutable data representation of a Liquidacion de Compras.
 */
class LiquidacionCompra(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val proveedor: Proveedor,
    val valores: Valores,
    val reembolso: Reembolso?,
    val detalles: List<ComprobanteDetalle>
    ) : ComprobanteElectronicoBase<LiquidacionCompra>(secuencial, fechaEmision, emisor) {

    data class Valores(
        val totals: Totals,
        val pagos: List<Pago>,
        val moneda: Moneda = Moneda.DOLAR,
    )

    data class Totals(
        val totalSinImpuestos: UDecimalValue,
        val totalDescuento: UDecimalValue,
        val totalConImpuestos: Map<ImpuestoIdentidad, ImpuestoLiquidacionTotal>,
        val importeTotal: UDecimalValue,
    )

    data class Reembolso(
        val codDocReembolso: CodeValue,
        val totals: ReembolsoTotals
    )

    data class ReembolsoTotals(
        val totalComprobantesReembolso: UDecimalValue,
        val totalBaseImponibleReembolso: UDecimalValue,
        val totalImpuestoReembolso: UDecimalValue
    )
}
