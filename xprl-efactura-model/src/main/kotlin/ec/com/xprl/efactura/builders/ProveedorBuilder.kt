package ec.com.xprl.efactura.builders

import ec.com.xprl.efactura.Proveedor
import ec.com.xprl.efactura.IdentityValue
import ec.com.xprl.efactura.TextValue

/**
 * Mutable builder for a [Proveedor].
 */
class ProveedorBuilder: AbstractBuilder<ProveedorBuilder, Proveedor>(
    Proveedor::class.java,
    requires("identifiación") { it.identificación },
    requires("razónSocial") { it.razónSocial }
) {
    private var identificación: IdentityValue? = null
    private var razónSocial: TextValue? = null
    private var dirección: TextValue? = null

    fun setIdentificacion(value: IdentityValue) = apply { identificación = value }
    fun setRazonSocial(value: TextValue) = apply { razónSocial = value }
    fun setDireccion(value: TextValue) = apply { dirección = value }

    operator fun plus(other: ProveedorBuilder) = merge(other)
    fun merge(other: ProveedorBuilder) = apply {
        other.identificación?.let { setIdentificacion(it) }
        other.razónSocial?.let { setRazonSocial(it) }
        other.dirección?.let { setDireccion(it) }
    }

    override fun validatedBuild() = Proveedor(
        identificación!!,
        razónSocial!!,
        dirección
    )
}