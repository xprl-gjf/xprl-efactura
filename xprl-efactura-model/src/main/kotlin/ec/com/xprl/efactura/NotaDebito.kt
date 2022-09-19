package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

/**
 * Immutable data representation of a NotaDebito.
 */
class NotaDebito(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val comprador: Comprador,
    val debito: Debito,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
) : ComprobanteElectronicoBase<NotaDebito>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

    data class Debito(
        val documentoModificado: DocModificado,
        val valores: Valores,
        val pagos: List<Pago>,
        val motivos: List<Motivo>,
        val rise: Rise? = null,
    )

    data class Valores(
        val totalSinImpuestos: UDecimalValue,
        val valorTotal: UDecimalValue,
        val impuestos: Map<ImpuestoIdentidad, ImpuestoTotal>,
    )

    data class Motivo(
        val razon: TextValue,
        val valor: UDecimalValue
    )
}
