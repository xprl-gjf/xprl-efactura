package ec.com.xprl.efactura.builders.factura

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.*
import ec.com.xprl.efactura.builders.liquidacion.ReembolsoBuilder
import ec.com.xprl.efactura.builders.liquidacion.ReembolsoDetalleBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Common interface for implementations of FacturaBuilder
 */
interface IFacturaBuilder: Builder<Factura> {
    fun setSecuencial(value: SecuencialValue): IFacturaBuilder
    fun setFechaEmision(value: LocalDate): IFacturaBuilder
    fun setEmisor(value: EmisorBuilder): IFacturaBuilder
    fun updateEmisor(value: EmisorBuilder): IFacturaBuilder
    fun setComprador(value: CompradorBuilder): IFacturaBuilder
    fun updateComprador(value: CompradorBuilder): IFacturaBuilder
    fun setValores(value: ValoresBuilder): IFacturaBuilder
    fun updateValores(value: ValoresBuilder): IFacturaBuilder
    fun setDetalles(vararg values: ComprobanteDetalleBuilder): IFacturaBuilder
    fun setDetalles(values: List<ComprobanteDetalleBuilder>): IFacturaBuilder
    fun updateDetalles(values: List<ComprobanteDetalleBuilder>): IFacturaBuilder
    fun setReembolso(value: ReembolsoBuilder?): IFacturaBuilder
    fun updateReembolso(value: ReembolsoBuilder): IFacturaBuilder
    fun setReembolsoDetalles(vararg values: ReembolsoDetalleBuilder): IFacturaBuilder
    fun setReembolsoDetalles(values: List<ReembolsoDetalleBuilder>?): IFacturaBuilder
    fun updateReembolsoDetalles(values: List<ReembolsoDetalleBuilder>): IFacturaBuilder
    fun setRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>?): IFacturaBuilder
    fun updateRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>): IFacturaBuilder
    fun setTipoNegocible(value: TipoNegociableBuilder?): IFacturaBuilder
    fun updateTipoNegociable(value: TipoNegociableBuilder): IFacturaBuilder
    fun setMaquinaFiscal(value: MaquinaFiscal?): IFacturaBuilder
    fun setInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>): IFacturaBuilder
    fun setInfoAdicional(values: InfoAdicional?): IFacturaBuilder
    fun updateInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>): IFacturaBuilder
    fun updateInfoAdicional(values: InfoAdicional): IFacturaBuilder
}

/**
 * Mutable builder for a [Factura].
 */
class FacturaBuilder: CompositeBuilder<FacturaBuilder, Factura>(
    Factura::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.emisor,
        builder.comprador,
        builder.valores,
        builder.reembolso,
        builder.tipoNegociable,
    ) + (
        builder.detalles ?: emptyList()
    ) + (
        builder.reembolsoDetalles ?: emptyList()
    )},
    requires("sequencial") { it.secuencial},
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("comprador") { it.comprador},
    requires("valores") { it.valores },
    requiresNotEmpty("detalles") { it.detalles },
    requiresNotMoreThan("infoAdicionales", 15) { it.infoAdicional }
), IFacturaBuilder {
    internal var secuencial: SecuencialValue? = null
    internal var fechaEmision: LocalDate? = null
    internal var emisor: EmisorBuilder? = null
    internal var comprador: CompradorBuilder? = null
    internal var valores: ValoresBuilder? = null
    internal var detalles: List<ComprobanteDetalleBuilder>? = null
    internal var reembolso: ReembolsoBuilder? = null
    internal var reembolsoDetalles: List<ReembolsoDetalleBuilder>? = null
    internal var retenciones: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>? = null
    internal var tipoNegociable: TipoNegociableBuilder? = null
    internal var maquinaFiscal: MaquinaFiscal? = null
    internal var infoAdicional: InfoAdicional? = null

    override fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    override fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    override fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    override fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    override fun setComprador(value: CompradorBuilder) = apply { comprador = value }
    override fun updateComprador(value: CompradorBuilder) = apply {
        comprador = if (comprador == null) { value } else { comprador!! + value }
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
    override fun setRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>?) = apply { retenciones = values }
    override fun updateRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>) = apply {
        retenciones = if (retenciones == null) { values } else { retenciones!! + values }
    }
    override fun setTipoNegocible(value: TipoNegociableBuilder?) = apply { tipoNegociable = value }
    override fun updateTipoNegociable(value: TipoNegociableBuilder) = apply {
        tipoNegociable = if (tipoNegociable == null) { value } else { tipoNegociable!! + value }
    }
    override fun setMaquinaFiscal(value: MaquinaFiscal?) = apply { maquinaFiscal = value }
    override fun setInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = setInfoAdicional(values.toMap())
    override fun setInfoAdicional(values: InfoAdicional?) = apply { infoAdicional = values }
    override fun updateInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = updateInfoAdicional(values.toMap())
    override fun updateInfoAdicional(values: InfoAdicional) = apply {
        infoAdicional = if (infoAdicional == null) { values } else { infoAdicional!! + values }
    }

    operator fun plus(other: FacturaBuilder) = merge(other)
    fun merge(other: FacturaBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.comprador?.let { updateComprador(it) }
        other.valores?.let { updateValores(it) }
        other.detalles?.let { updateDetalles(it) }
        other.reembolso?.let { updateReembolso(it) }
        other.reembolsoDetalles?.let { updateReembolsoDetalles(it) }
        other.retenciones?.let { updateRetenciones(it) }
        other.tipoNegociable?.let { updateTipoNegociable(it) }
        other.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun validatedBuild() = Factura(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        comprador!!.build(),
        valores!!.build(),
        detalles!!.map { it.build() },
        reembolso?.build(),
        reembolsoDetalles?.map { it.build() },
        retenciones,
        tipoNegociable?.build(),
        maquinaFiscal,
        infoAdicional
    )
}


/**
 * Mutable builder for a [Factura] that includes mandatory reembolsos.
 *
 * Behaves as a [FacturaBuilder], but with a constraint that
 * `reembolso` and `reembolsoDetalles` fields are mandatory.
 */
class FacturaForReembolsosBuilder(private val inner: FacturaBuilder): CompositeBuilder<FacturaForReembolsosBuilder, Factura>(
    Factura::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.inner.emisor,
        builder.inner.comprador,
        builder.inner.valores,
        builder.inner.reembolso,
        builder.inner.tipoNegociable
    ) + (
    builder.inner.detalles ?: emptyList()
    ) + (
    builder.inner.reembolsoDetalles ?: emptyList()
    )},
    requires("sequencial") { it.inner.secuencial},
    requires("fechaEmision") { it.inner.fechaEmision },
    requires("emisor") { it.inner.emisor },
    requires("comprador") { it.inner.comprador},
    requires("valores") { it.inner.valores },
    requiresNotEmpty("detalles") { it.inner.detalles },
    requires("reembolso") { it.inner.reembolso },
    requiresNotEmpty("reembolsoDetalles") { it.inner.reembolsoDetalles },
    requiresNotMoreThan("infoAdicionales", 15) { it.inner.infoAdicional }
), IFacturaBuilder by inner {

    override val isValid: Boolean
        get() = super.isValid

    override val invalidProperties: List<String>
        get() = super.invalidProperties


    operator fun plus(other: FacturaForReembolsosBuilder) = merge(other)
    fun merge(other: FacturaForReembolsosBuilder) = apply {
        other.inner.secuencial?.let { setSecuencial(it) }
        other.inner.fechaEmision?.let { setFechaEmision(it) }
        other.inner.emisor?.let { updateEmisor(it) }
        other.inner.comprador?.let { updateComprador(it) }
        other.inner.valores?.let { updateValores(it) }
        other.inner.detalles?.let { updateDetalles(it) }
        other.inner.reembolso?.let { updateReembolso(it) }
        other.inner.reembolsoDetalles?.let { updateReembolsoDetalles(it) }
        other.inner.retenciones?.let { updateRetenciones(it) }
        other.inner.tipoNegociable?.let { updateTipoNegociable(it) }
        other.inner.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.inner.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun build(): Factura = super.build()

    override fun validatedBuild() = inner.build()
}

