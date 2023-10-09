package uk.co.xprl.efactura.builders.factura

import uk.co.xprl.efactura.CorreoValue
import uk.co.xprl.efactura.Factura
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires

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