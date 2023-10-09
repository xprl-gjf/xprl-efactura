package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.ComprobanteRetencion
import uk.co.xprl.efactura.IdentityValue
import uk.co.xprl.efactura.TextValue
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires


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
