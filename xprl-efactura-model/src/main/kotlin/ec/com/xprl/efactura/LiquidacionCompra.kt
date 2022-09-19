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
    val detalles: List<ComprobanteDetalle>,
    val reembolso: Reembolso? = null,
    val reembolsoDetalles: List<ReembolsoDetalle>? = null,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
    ) : ComprobanteElectronicoBase<LiquidacionCompra>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

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

}
