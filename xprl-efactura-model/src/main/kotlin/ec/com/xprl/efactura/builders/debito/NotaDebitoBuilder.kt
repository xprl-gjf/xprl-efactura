package ec.com.xprl.efactura.builders.debito

import ec.com.xprl.efactura.NotaDebito
import ec.com.xprl.efactura.SecuencialValue
import ec.com.xprl.efactura.builders.*
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [NotaDebito]
 */
class NotaDebitoBuilder: CompositeBuilder<NotaDebitoBuilder, NotaDebito>(
    NotaDebito::class.java,
    innerBuilderProperties = listOf(
        { it.emisor },
        { it.comprador },
        { it.debito }
    ),
    requires("secuencial") { it.secuencial },
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("comprador") { it.comprador },
    requires("debito") { it.debito },
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var comprador: CompradorBuilder? = null
    private var debito: DebitoBuilder? = null

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
    fun setDebito(value: DebitoBuilder) = apply { debito = value }
    fun updateDebito(value: DebitoBuilder) = apply {
        debito = if (debito == null) { value } else { debito!! + value }
    }

    operator fun plus(other: NotaDebitoBuilder) = merge(other)
    fun merge(other: NotaDebitoBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.comprador?.let { updateComprador(it) }
        other.debito?.let { updateDebito(it) }
    }

    override fun validatedBuild() = NotaDebito(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        comprador!!.build(),
        debito!!.build(),
    )
}