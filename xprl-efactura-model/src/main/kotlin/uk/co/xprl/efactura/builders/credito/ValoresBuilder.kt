package uk.co.xprl.efactura.builders.credito

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [NotaCredito.Valores].
 */
class ValoresBuilder: AbstractBuilder<ValoresBuilder, NotaCredito.Valores>(
    NotaCredito.Valores::class.java,
    requires("totalSinImpuestos") { it.totalSinImpuestos },
    requires("valorModificacion") { it.valorModificacion },
    requiresNotEmpty("totalConImpuestos") { it.totalConImpuestos },
    requires("moneda") { it.moneda }
) {
    private var totalSinImpuestos: UDecimalValue? = null
    private var valorModificacion: UDecimalValue? = null
    private var totalConImpuestos: Map<ImpuestoIdentidad, ImpuestoTotal>? = null
    private var moneda: Moneda? = null

    fun setTotalSinImpuestos(value: UDecimalValue) = apply { totalSinImpuestos = value }
    fun setValorModificacion(value: UDecimalValue) = apply { valorModificacion = value }
    fun setTotalConImpuestos(value: Map<ImpuestoIdentidad, ImpuestoTotal>) = apply { totalConImpuestos = value }
    fun updateTotalConImpuestos(value: Map<ImpuestoIdentidad, ImpuestoTotal>) = apply {
        totalConImpuestos = if (totalConImpuestos == null) { value } else { totalConImpuestos!! + value }
    }
    fun setMoneda(value: Moneda? = null) = apply { moneda = value }

    operator fun plus(other: ValoresBuilder) = merge(other)
    fun merge(other: ValoresBuilder) = apply {
        other.totalSinImpuestos?.let { setTotalSinImpuestos(it) }
        other.valorModificacion?.let { setValorModificacion(it) }
        other.totalConImpuestos?.let { updateTotalConImpuestos(it) }
        other.moneda?.let { setMoneda(it) }
    }
    override fun validatedBuild() = NotaCredito.Valores(
        totalSinImpuestos!!,
        valorModificacion!!,
        totalConImpuestos!!,
        moneda!!
    )
}