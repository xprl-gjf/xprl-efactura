package ec.com.xprl.efactura.builders.debito

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [NotaDebito.Valores].
 */
class ValoresBuilder: AbstractBuilder<ValoresBuilder, NotaDebito.Valores>(
    NotaDebito.Valores::class.java,
    requires("totalSinImpuestos") { it.totalSinImpuestos },
    requires("valorTotal") { it.valorTotal },
    requiresNotEmpty("impuestos") { it.impuestos },
) {
    private var totalSinImpuestos: UDecimalValue? = null
    private var valorTotal: UDecimalValue? = null
    private var impuestos: Map<ImpuestoIdentidad, ImpuestoTotal>? = null

    fun setTotalSinImpuestos(value: UDecimalValue) = apply { totalSinImpuestos = value }
    fun setValorTotal(value: UDecimalValue) = apply { valorTotal = value }
    fun setImpuestos(value: Map<ImpuestoIdentidad, ImpuestoTotal>) = apply { impuestos = value }
    fun updateImpuestos(value: Map<ImpuestoIdentidad, ImpuestoTotal>) = apply {
        impuestos = if (impuestos == null) { value } else { impuestos!! + value }
    }

    operator fun plus(other: ValoresBuilder) = merge(other)
    fun merge(other: ValoresBuilder) = apply {
        other.totalSinImpuestos?.let { setTotalSinImpuestos(it) }
        other.valorTotal?.let { setValorTotal(it) }
        other.impuestos?.let { updateImpuestos(it) }
    }
    override fun validatedBuild() = NotaDebito.Valores(
        totalSinImpuestos!!,
        valorTotal!!,
        impuestos!!,
    )
}