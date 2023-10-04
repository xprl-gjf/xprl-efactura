package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.DocSustentoATS
import uk.co.xprl.efactura.builders.CompositeBuilder
import uk.co.xprl.efactura.builders.liquidacion.ReembolsoDetalleBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty

class ReembolsosATSBuilder: CompositeBuilder<ReembolsosATSBuilder, DocSustentoATS.ReembolsoATS>(
    DocSustentoATS.ReembolsoATS::class.java,
    innerBuilderProperties = { builder ->
        builder.detalles ?: emptyList()
    },
    requires("totals") { it.totals },
    requiresNotEmpty("detalles") { it.detalles }
) {
    private var totals: DocSustentoATS.ReembolsoTotals? = null
    private var detalles: List<ReembolsoDetalleBuilder>? = null

    fun setTotals(value: DocSustentoATS.ReembolsoTotals) = apply { totals = value }
    fun setDetalles(value: List<ReembolsoDetalleBuilder>) = apply { detalles = value }
    fun updateDetalles(value: List<ReembolsoDetalleBuilder>) = apply {
        detalles = if (detalles == null) { value } else { detalles!! + value }
    }

    operator fun plus(other: ReembolsosATSBuilder) = apply { merge(other) }
    fun merge(other: ReembolsosATSBuilder) = apply {
        other.totals?.let { setTotals(it) }
        other.detalles?.let { updateDetalles(it) }
    }

    override fun validatedBuild() = DocSustentoATS.ReembolsoATS(
        totals!!,
        detalles!!.map { it.build() }
    )
}