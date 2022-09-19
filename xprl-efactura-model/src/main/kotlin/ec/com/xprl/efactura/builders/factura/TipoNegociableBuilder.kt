package ec.com.xprl.efactura.builders.factura

import ec.com.xprl.efactura.CorreoValue
import ec.com.xprl.efactura.Factura
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires

/**
 * Mutable builder for [Factura.TipoNegociable].
 */
class TipoNegociableBuilder: AbstractBuilder<TipoNegociableBuilder, Factura.TipoNegociable>(
    Factura.TipoNegociable::class.java,
    requires("correo") { it.correo }
) {
    private var correo: CorreoValue? = null

    fun setCorreo(value: CorreoValue) = apply { correo = value }

    operator fun plus(other: TipoNegociableBuilder) = merge(other)
    fun merge(other: TipoNegociableBuilder) = apply {
        other.correo?.let { setCorreo(it) }
    }

    override fun validatedBuild() = Factura.TipoNegociable(
        correo!!
    )
}