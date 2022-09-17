package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

/**
 * Immutable data representation of a Factura.
 */
class Factura(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val comprador: Comprador,
    val valores: Valores,
    val detalles: List<ComprobanteDetalle>,
    val retenciones: Map<ImpuestoRetencionIvaPresuntivoYRenta, Retencion>? = null  // v1.1.0 only
    ) : ComprobanteElectronicoBase<Factura>(secuencial, fechaEmision, emisor) {

    data class Valores(
        val totals: Totals,
        val pagos: List<Pago>,
        val propina: UDecimalValue? = null,
        val moneda: Moneda = Moneda.DOLAR,
        val retIva: UDecimalValue? = null,
        val retRenta: UDecimalValue? = null,
    )

    data class Totals(
        val totalSinImpuestos: UDecimalValue,
        val totalDescuento: UDecimalValue,
        val totalConImpuestos: Map<ImpuestoIdentidad, ImpuestoTotal>,
        val importeTotal: UDecimalValue,
    )

    data class Retencion(
        val tarifa: UDecimalValue,
        val valor: UDecimalValue
    )
}
