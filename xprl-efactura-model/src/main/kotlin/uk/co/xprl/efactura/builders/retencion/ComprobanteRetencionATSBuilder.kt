package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.CompositeBuilder
import uk.co.xprl.efactura.builders.EmisorBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotMoreThan
import kotlinx.datetime.LocalDate
import uk.co.xprl.efactura.builders.factura.IFacturaBuilder

class ComprobanteRetencionATSBuilder : CompositeBuilder<ComprobanteRetencionATSBuilder, ComprobanteRetencionATS>(
    ComprobanteRetencionATS::class.java,
    innerBuilderProperties = listOf(
        { it.emisor },
        { it.sujeto },
        { it.valores }
    ),
    requires("secuencial") { it.secuencial },
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("sujeto") { it.sujeto },
    requires("valores") { it.valores },
    requiresNotMoreThan("infoAdicional", 15) { it.infoAdicional }
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var sujeto: SujetoATSBuilder? = null
    private var valores: ValoresATSBuilder? = null
    private var maquinaFiscal: MaquinaFiscal? = null
    private var infoAdicional: InfoAdicional? = null

    fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    fun setSujeto(value: SujetoATSBuilder) = apply { sujeto = value }
    fun updateSujeto(value: SujetoATSBuilder) = apply {
        sujeto = if (sujeto == null) { value } else { sujeto!! + value }
    }
    fun setValores(value: ValoresATSBuilder) = apply { valores = value }
    fun updateValores(value: ValoresATSBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }
    fun setMaquinaFiscal(value: MaquinaFiscal?) = apply { maquinaFiscal = value }
    fun setInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = setInfoAdicional(values.toMap())
    fun setInfoAdicional(values: InfoAdicional?) = apply { infoAdicional = values }
    fun updateInfoAdicional(vararg values: Pair<TextValue, MultiLineTextValue>) = updateInfoAdicional(values.toMap())
    fun updateInfoAdicional(values: InfoAdicional) = apply {
        infoAdicional = if (infoAdicional == null) { values } else { infoAdicional!! + values }
    }

    operator fun plus(other: ComprobanteRetencionATSBuilder) = merge(other)
    fun merge(other: ComprobanteRetencionATSBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.sujeto?.let { updateSujeto(it) }
        other.valores?.let { updateValores(it) }
        other.maquinaFiscal?.let { setMaquinaFiscal(it) }
        other.infoAdicional?.let { updateInfoAdicional(it) }
    }

    override fun validatedBuild() = ComprobanteRetencionATS(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        sujeto!!.build(),
        valores!!.build(),
        maquinaFiscal,
        infoAdicional
    )
}