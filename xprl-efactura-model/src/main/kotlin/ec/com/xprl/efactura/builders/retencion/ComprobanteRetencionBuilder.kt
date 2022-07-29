package ec.com.xprl.efactura.builders.retencion

import ec.com.xprl.efactura.ComprobanteRetencion
import ec.com.xprl.efactura.SecuencialValue
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.EmisorBuilder
import ec.com.xprl.efactura.builders.requires
import kotlinx.datetime.LocalDate

class ComprobanteRetencionBuilder : CompositeBuilder<ComprobanteRetencionBuilder, ComprobanteRetencion>(
    ComprobanteRetencion::class.java,
    innerBuilderProperties = listOf(
        { it.emisor },
        { it.sujecto },
        { it.valores }
    ),
    requires("secuencial") { it.secuencial },
    requires("fechaEmision") { it.fechaEmision },
    requires("emisor") { it.emisor },
    requires("sujecto") { it.sujecto },
    requires("valores") { it.valores },
) {
    private var secuencial: SecuencialValue? = null
    private var fechaEmision: LocalDate? = null
    private var emisor: EmisorBuilder? = null
    private var sujecto: SujectoBuilder? = null
    private var valores: ValoresBuilder? = null

    fun setSecuencial(value: SecuencialValue) = apply { secuencial = value }
    fun setFechaEmision(value: LocalDate) = apply { fechaEmision = value}
    fun setEmisor(value: EmisorBuilder) = apply { emisor = value }
    fun updateEmisor(value: EmisorBuilder) = apply {
        emisor = if (emisor == null) { value } else { emisor!! + value }
    }
    fun setSujecto(value: SujectoBuilder) = apply { sujecto = value }
    fun updateSujecto(value: SujectoBuilder) = apply {
        sujecto = if (sujecto == null) { value } else { sujecto!! + value }
    }
    fun setValores(value: ValoresBuilder) = apply { valores = value }
    fun updateValores(value: ValoresBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }

    operator fun plus(other: ComprobanteRetencionBuilder) = merge(other)
    fun merge(other: ComprobanteRetencionBuilder) = apply {
        other.secuencial?.let { setSecuencial(it) }
        other.fechaEmision?.let { setFechaEmision(it) }
        other.emisor?.let { updateEmisor(it) }
        other.sujecto?.let { updateSujecto(it) }
        other.valores?.let { updateValores(it) }
    }

    override fun validatedBuild() = ComprobanteRetencion(
        secuencial!!,
        fechaEmision!!,
        emisor!!.build(),
        sujecto!!.build(),
        valores!!.build()
    )
}