package uk.co.xprl.efactura.builders.remision

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotMoreThan

/**
 * Mutable builder for [GuiaRemision.DestinatarioDetalle].
 */
class DestinatarioDetalleBuilder : AbstractBuilder<DestinatarioDetalleBuilder, GuiaRemision.DestinatarioDetalle>(
    GuiaRemision.DestinatarioDetalle::class.java,
    requires("descripción") { it.descripción },
    requires("cantidad") { it.cantidad },
    requiresNotMoreThan("detalleAdicional", 3) { it.detalleAdicional }
) {
    private var descripción: TextValue? = null
    private var cantidad: UDecimalValue? = null
    private var codigoInterno: AlphanumericCodeValue? = null
    private var codigoAdicional: AlphanumericCodeValue? = null
    private var detalleAdicional: DetalleAdicional? = null

    fun setDescripcion(value: TextValue) = apply { descripción = value }
    fun setCantidad(value: UDecimalValue) = apply { cantidad = value }
    fun setCodigoInterno(value: AlphanumericCodeValue?) = apply { codigoInterno = value }
    fun setCodigoAdicional(value: AlphanumericCodeValue?) = apply { codigoAdicional = value }
    fun setDetallesAdicionales(value: DetalleAdicional?) = apply { detalleAdicional = value }
    fun updateDetallesAdicionales(value: DetalleAdicional) = apply {
        detalleAdicional = if (detalleAdicional == null) {
            value
        } else {
            detalleAdicional!! + value
        }
    }

    operator fun plus(other: DestinatarioDetalleBuilder) = merge(other)
    fun merge(other: DestinatarioDetalleBuilder) = apply {
        other.descripción?.let { setDescripcion(it) }
        other.cantidad?.let { setCantidad(it) }
        other.codigoInterno?.let { setCodigoInterno(it) }
        other.codigoAdicional?.let { setCodigoAdicional(it) }
        other.detalleAdicional?.let { updateDetallesAdicionales(it) }
    }

    override fun validatedBuild() = GuiaRemision.DestinatarioDetalle(
        descripción!!,
        cantidad!!,
        codigoInterno,
        codigoAdicional,
        detalleAdicional
    )
}
