package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.ImpuestoRetencionIdentidad
import uk.co.xprl.efactura.ImpuestoRetencionValor
import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.*
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for [ComprobanteRetencion.Valores].
 */
class ValoresBuilder: CompositeBuilder<ValoresBuilder, ComprobanteRetencion.Valores>(
    ComprobanteRetencion.Valores::class.java,
    innerBuilderProperties = { builder ->
        builder.impuestos?.map { it.value} ?: emptyList()
    },
    requires("periodoFiscal") { it.periodoFiscal },
    requiresNotEmpty("impuestos") { it.impuestos }
) {
    private var periodoFiscal: LocalDate? = null
    private var impuestos: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionBuilder>? = null

    fun setPeriodoFiscal(value: LocalDate) = apply { periodoFiscal = value }
    fun setImpuestos(value: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionBuilder>) = apply { impuestos = value }
    fun updateImpuestos(value: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionBuilder>) = apply {
        impuestos = if (impuestos == null) { value } else { impuestos!! + value }
    }

    operator fun plus(other: ValoresBuilder) = merge(other)
    fun merge(other: ValoresBuilder) = apply {
        other.periodoFiscal?.let { setPeriodoFiscal(it) }
        other.impuestos?.let { updateImpuestos(it) }
    }

    override fun validatedBuild() = ComprobanteRetencion.Valores(
        periodoFiscal!!,
        impuestos!!.mapValues { (_, v) -> v.build() },
    )
}

/**
 * Mutable builder for [ComprobanteRetencion.Valores].
 */
class ImpuestoRetencionBuilder: CompositeBuilder<ImpuestoRetencionBuilder, ComprobanteRetencion.ImpuestoRetencion>(
    ComprobanteRetencion.ImpuestoRetencion::class.java,
    innerBuilderProperties = listOf(
        { it.documentoSustento }
    ),
    requires("documentoSustento") { it.documentoSustento },
    requires("impuestoValor") { it.impuestoValor }
) {
    private var documentoSustento: DocSustentoBuilder? = null
    private var impuestoValor: ImpuestoRetencionValor? = null

    fun setDocumentoSustento(value: DocModificadoBuilder) = apply { documentoSustento = value }
    fun updateDocumentoSustento(value: DocModificadoBuilder) = apply {
        documentoSustento = if (documentoSustento == null) { value } else { documentoSustento!! + value }
    }
    fun setImpuestoValor(value: ImpuestoRetencionValor) = apply { impuestoValor = value }

    operator fun plus(other: ImpuestoRetencionBuilder) = merge(other)
    fun merge(other: ImpuestoRetencionBuilder) = apply {
        other.documentoSustento?.let { updateDocumentoSustento(it) }
        other.impuestoValor?.let { setImpuestoValor(it) }
    }

    override fun validatedBuild() = ComprobanteRetencion.ImpuestoRetencion(
        impuestoValor!!,
        documentoSustento!!.build(),
    )
}