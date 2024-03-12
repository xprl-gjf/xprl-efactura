package uk.co.xprl.efactura.builders.retencion

import kotlinx.datetime.LocalDate
import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.*

class DocSustentoATSBuilder: CompositeBuilder<DocSustentoATSBuilder, DocSustentoATS>(
    DocSustentoATS::class.java,
    innerBuilderProperties = { builder -> listOf(
        builder.reembolsos
    ) },
    requires("tipoDocumento") { it.tipoDocumento },
    requires("fechaEmisionDocSustento") { it.fechaEmisionDocSustento },
    requiresNotEmpty("impuestos") { it.impuestos },
    requiresNotEmpty("retenciones") { it.retenciones },
    requiresNotEmptyIf({ it.tipoDocumento == DocSustentoATS.TipoDocSustentoATS.COMPROBANTE_DE_VENTA_EMITIDO_POR_REEMBOLSO }, "reembolsos") { it.reembolsos }
) {
    private var tipoDocumento: DocSustentoATS.TipoDocSustentoATS? = null
    private var numDocSustento: DocumentId? = null
    private var fechaEmisionDocSustento: LocalDate? = null
    private var fechaRegistroContable: LocalDate? = null
    // number, 10 or 37 or 49 digits
    private var numAutDocSustento: DocAutorizacionValue? = null
    private var impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>? = null
    private var retenciones: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionValor>? = null
    private var reembolsos: ReembolsosATSBuilder? = null

    fun setTipoDocumento(value: DocSustentoATS.TipoDocSustentoATS) = apply { tipoDocumento = value }
    fun setNumDocSustento(value: DocumentId?) = apply { numDocSustento = value }
    fun setFechaEmisionDocSustento(value: LocalDate) = apply { fechaEmisionDocSustento = value }
    fun setFechaRegistroContable(value: LocalDate?) = apply { fechaRegistroContable = value }
    fun setNumAutDocSustento(value: DocAutorizacionValue?) = apply { numAutDocSustento = value }
    fun setImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply { impuestos = value }
    fun updateImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply {
        impuestos = if (impuestos == null) { value } else { impuestos!! + value }
    }
    fun setRetenciones(value: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionValor>) = apply { retenciones = value }
    fun updateRetenciones(value: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionValor>) = apply {
        retenciones = if (retenciones == null) { value } else { retenciones!! + value }
    }
    fun setReembolsos(value: ReembolsosATSBuilder) = apply { reembolsos = value }

    operator fun plus(other: DocSustentoATSBuilder) = merge(other)
    fun merge(other: DocSustentoATSBuilder) = apply {
        other.tipoDocumento?.let { setTipoDocumento(it) }
        other.numDocSustento?.let { setNumDocSustento(it) }
        other.fechaEmisionDocSustento?.let { setFechaEmisionDocSustento(it) }
        other.fechaRegistroContable?.let { setFechaRegistroContable(it) }
        other.numAutDocSustento?.let { setNumAutDocSustento(it) }
        other.impuestos?.let { updateImpuestos(it) }
        other.retenciones?.let { updateRetenciones(it) }
        other.reembolsos?.let { setReembolsos(it) }
    }

    override fun validatedBuild() = DocSustentoATS(
        tipoDocumento!!,
        numDocSustento,
        fechaEmisionDocSustento!!,
        fechaRegistroContable,
        numAutDocSustento,
        impuestos!!,
        retenciones!!,
        reembolsos?.build()
    )
}

