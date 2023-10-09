package uk.co.xprl.efactura.builders.remision

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for [GuiaRemision.Transportista].
 */
class TransportistaBuilder : AbstractBuilder<TransportistaBuilder, GuiaRemision.Transportista> (
    GuiaRemision.Transportista::class.java,
    requires("identificación") { it.identificación },
    requires("razónSocial") { it.razónSocial },
    requires("fechaIniTransporte") { it.fechaIniTransporte },
    requires("fechaFinTransporte") { it.fechaFinTransporte },
    requires("placa") { it.placa }
) {
    private var identificación: IdentityValue.RUC? = null
    private var razónSocial: TextValue? = null
    private var fechaIniTransporte: LocalDate? = null
    private var fechaFinTransporte: LocalDate? = null
    private var placa: ShortTextValue? = null

    fun setIdentificacion(value: IdentityValue.RUC) = apply { identificación = value }
    fun setRazonSocial(value: TextValue) = apply { razónSocial = value }
    fun setFechaIniTransporte(value: LocalDate) = apply { fechaIniTransporte = value }
    fun setFechaFinTransporte(value: LocalDate) = apply { fechaFinTransporte = value }
    fun setPlaca(value: ShortTextValue) = apply { placa = value }

    operator fun plus(other: TransportistaBuilder) = merge(other)
    fun merge(other: TransportistaBuilder) = apply {
        other.identificación?.let { setIdentificacion(it) }
        other.razónSocial?.let { setRazonSocial(it) }
        other.fechaIniTransporte?.let { setFechaIniTransporte(it) }
        other.fechaFinTransporte?.let { setFechaFinTransporte(it) }
        other.placa?.let { setPlaca(it) }
    }

    override fun validatedBuild() = GuiaRemision.Transportista(
        identificación!!,
        razónSocial!!,
        fechaIniTransporte!!,
        fechaFinTransporte!!,
        placa!!
    )
}
