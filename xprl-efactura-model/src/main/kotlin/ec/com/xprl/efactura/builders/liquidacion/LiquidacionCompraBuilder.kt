package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.*
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [LiquidacionCompra].
 */
class LiquidacionCompraBuilder: CompositeBuilder<LiquidacionCompraBuilder, LiquidacionCompra>(
    LiquidacionCompra::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.emisor,
        builder.proveedor,
        builder.valores,
        *(builder.detalles ?: emptyList<Builder<*>>()).toTypedArray()
    ) },
    requires("sequencial") { it.secuencial},
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("proveedor") { it.proveedor},
    requires("valores") { it.valores },
    requiresNotEmpty("detalles") { it.detalles }
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var proveedor: ProveedorBuilder? = null
    private var valores: ValoresBuilder? = null
    private var detalles: List<ComprobanteDetalleBuilder>? = null

    fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    fun setProveedor(value: ProveedorBuilder) = apply { proveedor = value }
    fun updateProveedor(value: ProveedorBuilder) = apply {
        proveedor = if (proveedor == null) { value } else { proveedor!! + value }
    }
    fun setValores(value: ValoresBuilder) = apply { valores = value }
    fun updateValores(value: ValoresBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }
    fun setDetalles(vararg values: ComprobanteDetalleBuilder) = setDetalles(values.toList())
    fun setDetalles(values: List<ComprobanteDetalleBuilder>) = apply { detalles = values }
    fun updateDetalles(values: List<ComprobanteDetalleBuilder>) = apply {
        detalles = if (detalles == null) { values } else { detalles!! + values }
    }

    operator fun plus(other: LiquidacionCompraBuilder) = merge(other)
    fun merge(other: LiquidacionCompraBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.proveedor?.let { updateProveedor(it) }
        other.valores?.let { updateValores(it) }
        other.detalles?.let { updateDetalles(it) }
    }

    override fun validatedBuild() = LiquidacionCompra(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        proveedor!!.build(),
        valores!!.build(),
        detalles!!.map { it.build() }
    )
}

