package ec.com.xprl.efactura.builders.remision

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires

/**
 * Mutable builder for [GuiaRemision.DestinatarioDetalle].
 */
class DestinatarioDetalleBuilder : AbstractBuilder<DestinatarioDetalleBuilder, GuiaRemision.DestinatarioDetalle>(
    GuiaRemision.DestinatarioDetalle::class.java,
    requires("descripción") { it.descripción },
    requires("cantidad") { it.cantidad }
) {
    private var descripción: TextValue? = null
    private var cantidad: UDecimalValue? = null
    private var codigoInterno: AlphanumericCodeValue? = null
    private var codigoAdicional: AlphanumericCodeValue? = null
    private var detallesAdicionales: List<DetalleAdicionale>? = null

    fun setDescripcion(value: TextValue) = apply { descripción = value }
    fun setCantidad(value: UDecimalValue) = apply { cantidad = value }
    fun setCodigoInterno(value: AlphanumericCodeValue?) = apply { codigoInterno = value }
    fun setCodigoAdicional(value: AlphanumericCodeValue?) = apply { codigoAdicional = value }
    fun setDetallesAdicionales(value: List<DetalleAdicionale>?) =
        apply { detallesAdicionales = value }

    fun updateDetallesAdicionales(value: List<DetalleAdicionale>?) = apply {
        detallesAdicionales = if (detallesAdicionales == null) {
            value
        } else {
            detallesAdicionales!! + (value ?: emptyList())
        }
    }

    operator fun plus(other: DestinatarioDetalleBuilder) = merge(other)
    fun merge(other: DestinatarioDetalleBuilder) = apply {
        other.descripción?.let { setDescripcion(it) }
        other.cantidad?.let { setCantidad(it) }
        other.codigoInterno?.let { setCodigoInterno(it) }
        other.codigoAdicional?.let { setCodigoAdicional(it) }
        other.detallesAdicionales?.let { updateDetallesAdicionales(it) }
    }

    override fun validatedBuild() = GuiaRemision.DestinatarioDetalle(
        descripción!!,
        cantidad!!,
        codigoInterno,
        codigoAdicional,
        detallesAdicionales
    )
}
