package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.*
import uk.co.xprl.efactura.SustentoATS.TipoSustento

/**
 * Mutable builder for a [SustentoATS].
 */
class SustentoATSBuilder : CompositeBuilder<SustentoATSBuilder, SustentoATS>(
    SustentoATS::class.java,
    innerBuilderProperties = listOf(
        { it.documentoSustento },
        { it.pago }
    ),
    requires("tipoSustento") { it.tipoSustento },
    requires("documentoSustento") { it.documentoSustento },
    requires("pago") { it.pago },
    requires("totals") { it.totals },
    requiresNotEmpty("pagos") { it.pagos }
) {
    private var tipoSustento: TipoSustento? = null
    private var documentoSustento: DocSustentoATSBuilder? = null
    private var pago: PagoRetencionATSBuilder<*>? = null
    private var totals: SustentoATS.Totals? = null
    private var pagos: List<PagoATS>? = null

    fun setTipoSustento(value: TipoSustento) = apply { tipoSustento = value }
    fun setDocumentoSustento(value: DocSustentoATSBuilder) = apply { documentoSustento = value }
    fun updateDocumentoSustento(value: DocSustentoATSBuilder) = apply {
        documentoSustento = if (documentoSustento == null) { value } else { documentoSustento!! + value }
    }
    fun setPago(value: PagoRetencionATSBuilder<*>) = apply { pago = value }
    fun setTotals(value: SustentoATS.Totals) = apply { totals = value }
    fun setPagos(value: List<PagoATS>) = apply { pagos = value }
    fun updatePagos(value: List<PagoATS>) = apply {
        pagos = if (pagos == null) { value } else { pagos!! + value }
    }

    operator fun plus(other: SustentoATSBuilder) = merge(other)
    fun merge(other: SustentoATSBuilder) = apply {
        other.tipoSustento?.let { setTipoSustento(it) }
        other.documentoSustento?.let { updateDocumentoSustento(it) }
        other.pago?.let { setPago(it) }
        other.totals?.let { setTotals(it) }
        other.pagos?.let { updatePagos(it) }
    }

    override fun validatedBuild() = SustentoATS(
        tipoSustento!!,
        documentoSustento!!.build(),
        pago!!.build(),
        totals!!,
        pagos!!
    )
}
