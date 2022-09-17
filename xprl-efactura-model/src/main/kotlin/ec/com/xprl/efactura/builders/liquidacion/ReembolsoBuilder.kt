package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.CodeValue
import ec.com.xprl.efactura.Reembolso
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.requires

class ReembolsoBuilder: CompositeBuilder<ReembolsoBuilder, Reembolso>(
    Reembolso::class.java,
    innerBuilderProperties =  listOf{ it.totals },
    requires("totals") { it.totals },
    requires("codDocReembolso") { it.codDocReembolso }
) {
    private var totals: ReembolsoTotalsBuilder? = null
    private var codDocReembolso: CodeValue? = null

    fun setTotals(value: ReembolsoTotalsBuilder) = apply { totals = value }
    fun updateTotals(value: ReembolsoTotalsBuilder) = apply {
        totals = if (totals == null) { value } else { totals!! + value }
    }
    fun setCodDocReembolso(value: CodeValue) = apply { codDocReembolso = value }

    operator fun plus(other: ReembolsoBuilder) = merge(other)
    fun merge(other: ReembolsoBuilder) = apply {
        other.totals?.let { updateTotals(it) }
        other.codDocReembolso?.let { setCodDocReembolso(it) }
    }

    override fun validatedBuild() = Reembolso(
        codDocReembolso!!,
        totals!!.build(),
    )
}