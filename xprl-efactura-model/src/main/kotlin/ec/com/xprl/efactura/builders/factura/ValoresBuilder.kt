package ec.com.xprl.efactura.builders.factura

import ec.com.xprl.efactura.UDecimalValue
import ec.com.xprl.efactura.Factura
import ec.com.xprl.efactura.Moneda
import ec.com.xprl.efactura.Pago
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [Factura.Valores].
 */
class ValoresBuilder: CompositeBuilder<ValoresBuilder, Factura.Valores>(
    Factura.Valores::class.java,
    innerBuilderProperties =  listOf{ it.totals },
    requires("totals") { it.totals },
    requiresNotEmpty("pagos") { it.pagos },
    requires("moneda") { it.moneda }
) {
    private var totals: TotalsBuilder? = null
    private var pagos: List<Pago>? = null
    private var propina: UDecimalValue? = null
    private var moneda: Moneda? = null
    private var retIva: UDecimalValue? = null
    private var retRenta: UDecimalValue? = null

    fun setTotals(value: TotalsBuilder) = apply { totals = value }
    fun updateTotals(value: TotalsBuilder) = apply {
        totals = if (totals == null) { value } else { totals!! + value }
    }
    fun setPagos(vararg values: Pago) = setPagos(values.toList())
    fun setPagos(values: List<Pago>) = apply { pagos = values }
    fun updatePagos(value: List<Pago>) = apply {
        pagos = if (pagos == null) { value } else { pagos!! + value }
    }
    fun setPropina(value: UDecimalValue? = null) = apply { propina = value }
    fun setMoneda(value: Moneda? = null) = apply { moneda = value }
    fun setRetIva(value: UDecimalValue? = null) = apply { retIva = value }
    fun setRetRenta(value: UDecimalValue? = null) = apply { retRenta = value }

    operator fun plus(other: ValoresBuilder) = merge(other)
    fun merge(other: ValoresBuilder) = apply {
        other.totals?.let { updateTotals(it) }
        other.pagos?.let { updatePagos(it) }
        other.propina?.let { setPropina(it) }
        other.moneda?.let { setMoneda(it) }
        other.retIva?.let { setRetIva(it) }
        other.retRenta?.let { setRetRenta(it) }
    }
    override fun validatedBuild() = Factura.Valores(
        totals!!.build(),
        pagos!!,
        propina,
        moneda!!,
        retIva,
        retRenta
    )
}