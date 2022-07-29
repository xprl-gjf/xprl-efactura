package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [LiquidacionCompra.Valores].
 */
class ValoresBuilder: CompositeBuilder<ValoresBuilder, LiquidacionCompra.Valores>(
    LiquidacionCompra.Valores::class.java,
    innerBuilderProperties =  listOf{ it.totals },
    requires("totals") { it.totals },
    requiresNotEmpty("pagos") { it.pagos },
    requires("moneda") { it.moneda }
) {
    private var totals: TotalsBuilder? = null
    private var pagos: List<Pago>? = null
    private var moneda: Moneda? = null

    fun setTotals(value: TotalsBuilder) = apply { totals = value }
    fun updateTotals(value: TotalsBuilder) = apply {
        totals = if (totals == null) { value } else { totals!! + value }
    }
    fun setPagos(vararg values: Pago) = setPagos(values.toList())
    fun setPagos(values: List<Pago>) = apply { pagos = values }
    fun updatePagos(value: List<Pago>) = apply {
        pagos = if (pagos == null) { value } else { pagos!! + value }
    }
    fun setMoneda(value: Moneda? = null) = apply { moneda = value }

    operator fun plus(other: ValoresBuilder) = merge(other)
    fun merge(other: ValoresBuilder) = apply {
        other.totals?.let { updateTotals(it) }
        other.pagos?.let { updatePagos(it) }
        other.moneda?.let { setMoneda(it) }
    }
    override fun validatedBuild() = LiquidacionCompra.Valores(
        totals!!.build(),
        pagos!!,
    )
}