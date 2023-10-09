package uk.co.xprl.efactura.builders.liquidacion

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.*
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [LiquidacionCompra].
 */
class LiquidacionCompraBuilder: CompositeBuilder<LiquidacionCompraBuilder, LiquidacionCompra>(
    LiquidacionCompra::class.java,
    innerBuilderProperties = { builder -> listOf(
            builder.emisor,
            builder.proveedor,
            builder.valores
        ) + (
            builder.detalles ?: emptyList()
        )
    },
    requires("sequencial") { it.secuencial},
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("proveedor") { it.proveedor},
    requires("valores") { it.valores },
    requiresNotEmpty("detalles") { it.detalles },
    requiresNotMoreThan("infoAdicional", 15) { it.infoAdicional }
), ILiquidacionCompraBuilder {
    internal var secuencial: SecuencialValue? = null
    internal var fechaEmision: LocalDate? = null
    internal var emisor: EmisorBuilder? = null
    internal var proveedor: ProveedorBuilder? = null
    internal var valores: ValoresBuilder? = null
    internal var detalles: List<ComprobanteDetalleBuilder>? = null
    internal var reembolso: ReembolsoBuilder? = null
    internal var reembolsoDetalles: List<ReembolsoDetalleBuilder>? = null
    internal var maquinaFiscal: MaquinaFiscal? = null
    internal var infoAdicional: InfoAdicional? = null

    override fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    override fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    override fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    override fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    override fun setProveedor(value: ProveedorBuilder) = apply { proveedor = value }
    override fun updateProveedor(value: ProveedorBuilder) = apply {
        proveedor = if (proveedor == null) { value } else { proveedor!! + value }
    }
    override fun setValores(value: ValoresBuilder) = apply { valores = value }
    override fun updateValores(value: ValoresBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }
    override fun setDetalles(vararg values: ComprobanteDetalleBuilder) = setDetalles(values.toList())
    override fun setDetalles(values: List<ComprobanteDetalleBuilder>) = apply { detalles = values }
    override fun updateDetalles(values: List<ComprobanteDetalleBuilder>) = apply {
        detalles = if (detalles == null) { values } else { detalles!! + values }
    }
    override fun setReembolso(value: ReembolsoBuilder?) = apply { reembolso = value }
    override fun updateReembolso(value: ReembolsoBuilder) = apply {
        reembolso = if (reembolso == null) { value } else { reembolso!! + value }
    }
    override fun setReembolsoDetalles(vararg values: ReembolsoDetalleBuilder) = setReembolsoDetalles(values.toList())
    override fun setReembolsoDetalles(values: List<ReembolsoDetalleBuilder>?) = apply { reembolsoDetalles = values }
    override fun updateReembolsoDetalles(values: List<ReembolsoDetalleBuilder>) = apply {
        reembolsoDetalles = if (reembolsoDetalles == null) { values } else { reembolsoDetalles!! + values }
    }
    override fun setMaquinaFiscal(value: MaquinaFiscal?) = apply { maquinaFiscal = value }
    override fun setInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = setInfoAdicional(values.toMap())
    override fun setInfoAdicional(values: InfoAdicional?) = apply { infoAdicional = values }
    override fun updateInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = updateInfoAdicional(values.toMap())
    override fun updateInfoAdicional(values: InfoAdicional) = apply {
        infoAdicional = if (infoAdicional == null) { values } else { infoAdicional!! + values }
    }

    operator fun plus(other: LiquidacionCompraBuilder) = merge(other)
    fun merge(other: LiquidacionCompraBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.proveedor?.let { updateProveedor(it) }
        other.valores?.let { updateValores(it) }
        other.detalles?.let { updateDetalles(it) }
        other.reembolso?.let { updateReembolso(it) }
        other.reembolsoDetalles?.let { updateReembolsoDetalles(it) }
        other.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun validatedBuild() = LiquidacionCompra(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        proveedor!!.build(),
        valores!!.build(),
        detalles!!.map { it.build() },
        reembolso?.build(),
        reembolsoDetalles?.map { it.build() },
        maquinaFiscal,
        infoAdicional
    )
}

/**
 * Mutable builder for a [LiquidacionCompra] that includes mandatory reembolsos.
 *
 * Behaves as a [LiquidacionCompraBuilder], but with a constraint that
 * `reembolso` and `reembolsoDetalles` fields are mandatory.
 */
class LiquidacionCompraForReembolsosBuilder(private val inner: LiquidacionCompraBuilder): CompositeBuilder<LiquidacionCompraForReembolsosBuilder, LiquidacionCompra>(
    LiquidacionCompra::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.inner.emisor,
        builder.inner.proveedor,
        builder.inner.valores,
        builder.inner.reembolso,
        ) + (
        builder.inner.detalles ?: emptyList()
        ) + (
        builder.inner.reembolsoDetalles ?: emptyList()
        )
    },
    requires("sequencial") { it.inner.secuencial},
    requires("fechaEmision") { it.inner.fechaEmision },
    requires("emisor") { it.inner.emisor },
    requires("proveedor") { it.inner.proveedor},
    requires("valores") { it.inner.valores },
    requiresNotEmpty("detalles") { it.inner.detalles },
    requires("reembolso") { it.inner.reembolso },
    requiresNotEmpty("reembolseDetalles") { it.inner.reembolsoDetalles },
    requiresNotMoreThan("infoAdicional", 15) { it.inner.infoAdicional }
), ILiquidacionCompraBuilder by inner {

    override val isValid: Boolean
        get() = super.isValid

    override val invalidProperties: List<String>
        get() = super.invalidProperties


    operator fun plus(other: LiquidacionCompraForReembolsosBuilder) = merge(other)
    fun merge(other: LiquidacionCompraForReembolsosBuilder) = apply {
        other.inner.secuencial?.let { setSecuencial(it) }
        other.inner.fechaEmision?.let { setFechaEmision(it) }
        other.inner.emisor?.let { updateEmisor(it) }
        other.inner.proveedor?.let { updateProveedor(it) }
        other.inner.valores?.let { updateValores(it) }
        other.inner.detalles?.let { updateDetalles(it) }
        other.inner.reembolso?.let { updateReembolso(it) }
        other.inner.reembolsoDetalles?.let { updateReembolsoDetalles(it) }
        other.inner.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.inner.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun build(): LiquidacionCompra = super.build()

    override fun validatedBuild() = inner.build()
}


/**
 * Common interface for implementations of LiquidacionCompraBuilder
 */
interface ILiquidacionCompraBuilder: Builder<LiquidacionCompra> {
    fun setSecuencial(value: SecuencialValue): ILiquidacionCompraBuilder
    fun setFechaEmision(value: LocalDate): ILiquidacionCompraBuilder
    fun setEmisor(value: EmisorBuilder): ILiquidacionCompraBuilder
    fun updateEmisor(value: EmisorBuilder): ILiquidacionCompraBuilder
    fun setProveedor(value: ProveedorBuilder): ILiquidacionCompraBuilder
    fun updateProveedor(value: ProveedorBuilder): ILiquidacionCompraBuilder
    fun setValores(value: ValoresBuilder): ILiquidacionCompraBuilder
    fun updateValores(value: ValoresBuilder): ILiquidacionCompraBuilder
    fun setDetalles(vararg values: ComprobanteDetalleBuilder): ILiquidacionCompraBuilder
    fun setDetalles(values: List<ComprobanteDetalleBuilder>): ILiquidacionCompraBuilder
    fun updateDetalles(values: List<ComprobanteDetalleBuilder>): ILiquidacionCompraBuilder
    fun setReembolso(value: ReembolsoBuilder?): ILiquidacionCompraBuilder
    fun updateReembolso(value: ReembolsoBuilder): ILiquidacionCompraBuilder
    fun setReembolsoDetalles(vararg values: ReembolsoDetalleBuilder): ILiquidacionCompraBuilder
    fun setReembolsoDetalles(values: List<ReembolsoDetalleBuilder>?): ILiquidacionCompraBuilder
    fun updateReembolsoDetalles(values: List<ReembolsoDetalleBuilder>): ILiquidacionCompraBuilder
    fun setMaquinaFiscal(value: MaquinaFiscal?): ILiquidacionCompraBuilder
    fun setInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>): ILiquidacionCompraBuilder
    fun setInfoAdicional(values: InfoAdicional?): ILiquidacionCompraBuilder
    fun updateInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>): ILiquidacionCompraBuilder
    fun updateInfoAdicional(values: InfoAdicional): ILiquidacionCompraBuilder
}


