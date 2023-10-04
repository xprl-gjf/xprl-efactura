package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.SustentoATS
import java.math.BigDecimal

/**
 * Immutable formatted representation of a sustento for a ComprobanteRetencionATS (schema v2.0.0).
 */
@Suppress("MemberVisibilityCanBePrivate")
class SustentoRetencion(
    val src: SustentoATS
) {
    val codigo: String
        get() = String.format("%02d", src.tipoSustento.codigo)

    val docSustento = DocSustentoATS(src.docSustento)

    val pago = PagoRetencionATS(src.pago)

    val totalComprobantesReembolso: BigDecimal?
        get() = src.docSustento.reembolsos?.totals?.totalComprobantesReembolso?.toBigDecimal()
    val totalBaseImponibleReembolso: BigDecimal?
        get() = src.docSustento.reembolsos?.totals?.totalBaseImponibleReembolso?.toBigDecimal()
    val totalImpuestoReembolso: BigDecimal?
        get() = src.docSustento.reembolsos?.totals?.totalImpuestoReembolso?.toBigDecimal()

    val totalSinImpuestos: BigDecimal
        get() = src.totals.totalSinImpuestos.toBigDecimal()
    val importeTotal: BigDecimal
        get() = src.totals.importeTotal.toBigDecimal()

    val impuestos: List<ImpuestoDocSustento> = src.docSustento.impuestos.map { (k, v) ->
        ImpuestoDocSustento(k, v)
    }

    val retenciones: List<RetencionATS> = src.docSustento.retenciones.map { (k, v) ->
        RetencionATS(k, v)
    }

    val reembolsos: List<ReembolsoDetalle>? = src.docSustento.reembolsos?.detalles?.map { ReembolsoDetalle(it) }

    val pagos: List<Pago> = src.pagos.map { Pago(it) }
}