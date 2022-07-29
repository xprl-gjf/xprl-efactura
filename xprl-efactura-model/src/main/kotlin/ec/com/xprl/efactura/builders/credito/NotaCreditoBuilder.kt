package ec.com.xprl.efactura.builders.credito

import ec.com.xprl.efactura.ComprobanteDetalle
import ec.com.xprl.efactura.NotaCredito
import ec.com.xprl.efactura.SecuencialValue
import ec.com.xprl.efactura.builders.*
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [NotaCredito]
 */
class NotaCreditoBuilder: CompositeBuilder<NotaCreditoBuilder, NotaCredito>(
    NotaCredito::class.java,
    innerBuilderProperties = listOf(
        { it.emisor },
        { it.comprador },
        { it.credito }
    ),
    requires("secuencial") { it.secuencial },
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("comprador") { it.comprador },
    requires("credito") { it.credito },
    requiresNotEmpty("detalles") { it.detalles }
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var comprador: CompradorBuilder? = null
    private var credito: CreditoBuilder? = null
    private var detalles: List<ComprobanteDetalle>? = null

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
    fun setCredito(value: CreditoBuilder) = apply { credito = value }
    fun updateCredito(value: CreditoBuilder) = apply {
        credito = if (credito == null) { value } else { credito!! + value }
    }
    fun setDetalles(vararg values: ComprobanteDetalle) = setDetalles(values.toList())
    fun setDetalles(values: List<ComprobanteDetalle>) = apply { detalles = values }
    fun updateDetalles(values: List<ComprobanteDetalle>) = apply {
        detalles = if (detalles == null) { values } else { detalles!! + values }
    }

    operator fun plus(other: NotaCreditoBuilder) = merge(other)
    fun merge(other: NotaCreditoBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.comprador?.let { updateComprador(it) }
        other.credito?.let { updateCredito(it) }
        other.detalles?.let { updateDetalles(it) }
    }

    override fun validatedBuild() = NotaCredito(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        comprador!!.build(),
        credito!!.build(),
        detalles!!
    )
}