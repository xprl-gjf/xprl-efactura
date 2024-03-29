package uk.co.xprl.efactura.builders

import uk.co.xprl.efactura.Comprador
import uk.co.xprl.efactura.IdentityValue
import uk.co.xprl.efactura.TextValue

/**
 * Mutable builder for a [Comprador].
 */
class CompradorBuilder: AbstractBuilder<CompradorBuilder, Comprador>(
    Comprador::class.java,
    requires("identifiación") { it.identificación },
    requires("razónSocial") { it.razónSocial }
) {
    private var identificación: IdentityValue? = null
    private var razónSocial: TextValue? = null
    private var dirección: TextValue? = null

    fun setIdentificacion(value: IdentityValue) = apply { identificación = value }
    fun setRazonSocial(value: TextValue) = apply { razónSocial = value }
    fun setDireccion(value: TextValue) = apply { dirección = value }

    operator fun plus(other: CompradorBuilder) = merge(other)
    fun merge(other: CompradorBuilder) = apply {
        other.identificación?.let { setIdentificacion(it) }
        other.razónSocial?.let { setRazonSocial(it) }
        other.dirección?.let { setDireccion(it) }
    }

    override fun validatedBuild()= Comprador(
        identificación!!,
        razónSocial!!,
        dirección
    )
}