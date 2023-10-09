package uk.co.xprl.efactura

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
    val reembolso: Reembolso? = null,
    val reembolsoDetalles: List<ReembolsoDetalle>? = null,
    val retenciones: Map<ImpuestoRetencionIvaPresuntivoYRenta, Retencion>? = null,  // v1.1.0 only
    val tipoNegociable: TipoNegociable? = null,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
    ) : ComprobanteElectronicoBase<Factura>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

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

    data class TipoNegociable(
        val correo: CorreoValue
    )
}
