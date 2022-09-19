package ec.com.xprl.efactura.builders.remision

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.*
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for a [GuiaRemision].
 */
class GuiaRemisionBuilder: CompositeBuilder<GuiaRemisionBuilder, GuiaRemision>(
    GuiaRemision::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.emisor,
        builder.remision,
        *(builder.destinatarios ?: emptyList<Builder<*>>()).toTypedArray()
    ) },
    requires("sequencial") { it.secuencial},
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("remision") { it.remision},
    requiresNotEmpty("destinatarios") { it.destinatarios },
    requiresNotMoreThan("infoAdicional", 15) { it.infoAdicional }
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var remision: RemisionBuilder? = null
    private var destinatarios: List<DestinatarioBuilder>? = null
    private var maquinaFiscal: MaquinaFiscal? = null
    private var infoAdicional: InfoAdicional? = null

    fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    fun setRemision(value: RemisionBuilder) = apply { remision = value }
    fun updateRemision(value: RemisionBuilder) = apply {
        remision = if (remision == null) { value } else { remision!! + value }
    }
    fun setDestinatarios(vararg values: DestinatarioBuilder) = setDestinatarios(values.toList())
    fun setDestinatarios(values: List<DestinatarioBuilder>) = apply { destinatarios = values }
    fun updateDestinatarios(values: List<DestinatarioBuilder>) = apply {
        destinatarios = if (destinatarios == null) { values } else { destinatarios!! + values }
    }
    fun setMaquinaFiscal(value: MaquinaFiscal?) = apply { maquinaFiscal = value }
    fun setInfoAdicional(vararg values: Pair<TextValue, TextValue>) = setInfoAdicional(values.toMap())
    fun setInfoAdicional(values: InfoAdicional?) = apply { infoAdicional = values }
    fun updateInfoAdicional(values: InfoAdicional) = apply {
        infoAdicional = if (infoAdicional == null) { values } else { infoAdicional!! + values }
    }

    operator fun plus(other: GuiaRemisionBuilder) = merge(other)
    fun merge(other: GuiaRemisionBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.remision?.let { updateRemision(it) }
        other.destinatarios?.let { updateDestinatarios(it) }
        other.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun validatedBuild() = GuiaRemision(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        remision!!.build(),
        destinatarios!!.map { it.build() },
        maquinaFiscal,
        infoAdicional
    )
}

