package ec.com.xprl.efactura.builders.factura

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.*
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [Factura].
 */
class FacturaBuilder: CompositeBuilder<FacturaBuilder, Factura>(
    Factura::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.emisor,
        builder.comprador,
        builder.valores
    ) + (
        builder.detalles ?: emptyList()
    ) },
    requires("sequencial") { it.secuencial},
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("comprador") { it.comprador},
    requires("valores") { it.valores },
    requiresNotEmpty("detalles") { it.detalles },
    requiresNotMoreThan("infoAdicionales", 15) { it.infoAdicional }
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var comprador: CompradorBuilder? = null
    private var valores: ValoresBuilder? = null
    private var detalles: List<ComprobanteDetalleBuilder>? = null
    private var retenciones: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>? = null
    private var infoAdicional: InfoAdicional? = null

    fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    fun setComprador(value: CompradorBuilder) = apply { comprador = value }
    fun updateComprador(value: CompradorBuilder) = apply {
        comprador = if (comprador == null) { value } else { comprador!! + value }
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
    fun setRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>?) = apply { retenciones = values }
    fun updateRetenciones(values: Map<ImpuestoRetencionIvaPresuntivoYRenta, Factura.Retencion>) = apply {
        retenciones = if (retenciones == null) { values } else { retenciones!! + values }
    }
    fun setInfoAdicional(vararg values: Pair<TextValue, TextValue>) = setInfoAdicional(values.toMap())
    fun setInfoAdicional(values: InfoAdicional?) = apply { infoAdicional = values }
    fun updateInfoAdicional(values: InfoAdicional) = apply {
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
        other.retenciones?.let { updateRetenciones(it) }
        other.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun validatedBuild() = Factura(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        comprador!!.build(),
        valores!!.build(),
        detalles!!.map { it.build() },
        retenciones,
        infoAdicional
    )
}

