package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

/**
 * Immutable data representation of a NotaCredito.
 */
class NotaCredito(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val comprador: Comprador,
    val credito: Credito,
    val detalles: List<ComprobanteDetalle>,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
) : ComprobanteElectronicoBase<Factura>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

    data class Credito(
        val documentoModificado: DocModificado,
        val motivo: TextValue,
        val valores: Valores,
        val rise: Rise? = null,
    )

    data class Valores(
        val totalSinImpuestos: UDecimalValue,
        val valorModificacion: UDecimalValue,
        val totalConImpuestos: Map<ImpuestoIdentidad, ImpuestoTotal>,
        val moneda: Moneda = Moneda.DOLAR,
    )
}
