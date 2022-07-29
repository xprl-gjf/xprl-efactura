package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [LiquidacionCompra.Totals].
 */
class TotalsBuilder: AbstractBuilder<TotalsBuilder, LiquidacionCompra.Totals>(
    LiquidacionCompra.Totals::class.java,
    requires("totalSinImpuestos") { it.totalSinImpuestos },
    requires("totalDescuento") { it.totalDescuento },
    requiresNotEmpty("totalConImpuestos") { it.totalConImpuestos },
    requires("importeTotal") { it.importeTotal }
) {
    private var totalSinImpuestos: UDecimalValue? = null
    private var totalDescuento: UDecimalValue? = null
    private var totalConImpuestos: Map<ImpuestoIdentidad, ImpuestoLiquidacionTotal>? = null
    private var importeTotal: UDecimalValue? = null

    fun setTotalSinImpuestos(value: UDecimalValue) = apply { totalSinImpuestos = value }
    fun setTotalDescuento(value: UDecimalValue) = apply { totalDescuento = value }
    fun setTotalConImpuestos(value: Map<ImpuestoIdentidad, ImpuestoLiquidacionTotal>) = apply { totalConImpuestos = value }
    fun updateTotalConImpuestos(value: Map<ImpuestoIdentidad, ImpuestoLiquidacionTotal>) = apply {
        totalConImpuestos = if (totalConImpuestos == null) { value } else { totalConImpuestos!! + value }
    }
    fun setImporteTotal(value: UDecimalValue) = apply { importeTotal = value }

    operator fun plus(other: TotalsBuilder) = merge(other)
    fun merge(other: TotalsBuilder) = apply {
        other.totalSinImpuestos?.let { setTotalSinImpuestos(it) }
        other.totalDescuento?.let { setTotalDescuento(it) }
        other.totalConImpuestos?.let { updateTotalConImpuestos(it) }
        other.importeTotal?.let { setImporteTotal(it) }
    }

    override fun validatedBuild() = LiquidacionCompra.Totals(
        totalSinImpuestos!!,
        totalDescuento!!,
        totalConImpuestos!!,
        importeTotal!!
    )
}