package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires

/**
 * Mutable builder for [Reembolso.ReembolsoTotals].
 */
class ReembolsoTotalsBuilder: AbstractBuilder<ReembolsoTotalsBuilder, Reembolso.ReembolsoTotals>(
    Reembolso.ReembolsoTotals::class.java,
    requires("totalComprobantesReembolso") { it.totalComprobantesReembolso },
    requires("totalBaseImponibleReembolso") { it.totalBaseImponibleReembolso },
    requires("totalImpuestoReembolso") { it.totalImpuestoReembolso },
) {
    private var totalComprobantesReembolso: UDecimalValue? = null
    private var totalBaseImponibleReembolso: UDecimalValue? = null
    private var totalImpuestoReembolso: UDecimalValue? = null

    fun setTotalComprobantesReembolso(value: UDecimalValue) = apply { totalComprobantesReembolso = value }
    fun setTotalBaseImponibleReembolso(value: UDecimalValue) = apply { totalBaseImponibleReembolso = value }
    fun setTotalImpuestoReembolso(value: UDecimalValue) = apply { totalImpuestoReembolso = value }

    operator fun plus(other: ReembolsoTotalsBuilder) = merge(other)
    fun merge(other: ReembolsoTotalsBuilder) = apply {
        other.totalComprobantesReembolso?.let { setTotalComprobantesReembolso(it) }
        other.totalComprobantesReembolso?.let { setTotalBaseImponibleReembolso(it) }
        other.totalComprobantesReembolso?.let { setTotalImpuestoReembolso(it) }
    }

    override fun validatedBuild() = Reembolso.ReembolsoTotals(
        totalComprobantesReembolso!!,
        totalBaseImponibleReembolso!!,
        totalImpuestoReembolso!!
    )
}