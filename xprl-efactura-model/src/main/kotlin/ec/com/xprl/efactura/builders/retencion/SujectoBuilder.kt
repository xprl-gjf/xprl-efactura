package ec.com.xprl.efactura.builders.retencion

import ec.com.xprl.efactura.ComprobanteRetencion
import ec.com.xprl.efactura.IdentityValue
import ec.com.xprl.efactura.TextValue
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires


/**
 * Mutable builder for [ComprobanteRetencion.Sujecto].
 */
class SujectoBuilder : AbstractBuilder<SujectoBuilder, ComprobanteRetencion.Sujecto>(
    ComprobanteRetencion.Sujecto::class.java,
    requires("identificación") { it.identificacion },
    requires("razónSocial") { it.razonSocial }
) {
    private var identificacion: IdentityValue? = null
    private var razonSocial: TextValue? = null

    fun setIdentificacion(value: IdentityValue) = apply { identificacion = value }
    fun setRazonSocial(value: TextValue) = apply { razonSocial = value }

    operator fun plus(other: SujectoBuilder) = merge(other)
    fun merge(other: SujectoBuilder) = apply {
        other.identificacion?.let { setIdentificacion(it) }
        other.razonSocial?.let { setRazonSocial(it) }
    }

    override fun validatedBuild() = ComprobanteRetencion.Sujecto(
        identificacion!!,
        razonSocial!!,
    )
}
