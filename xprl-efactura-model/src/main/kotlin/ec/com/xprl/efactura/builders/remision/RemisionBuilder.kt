package ec.com.xprl.efactura.builders.remision

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty


/**
 * Mutable builder for [GuiaRemision.Remision].
 */
class RemisionBuilder : CompositeBuilder<RemisionBuilder, GuiaRemision.Remision>(
    GuiaRemision.Remision::class.java,
    innerBuilderProperties = listOf(
        { it.transportista },
    ),
    requires("dirPartida") { it.dirPartida },
    requiresNotEmpty("transportista") { it.transportista }
) {
    private var dirPartida: TextValue? = null
    private var transportista: TransportistaBuilder? = null
    private var dirEstablecimiento: TextValue? = null
    private var contribuyenteEspecial: NumericIdentifier? = null
    private var obligadoLlevarCompt: Boolean? = null
    private var rise: Rise? = null

    fun setDirPartida(value: TextValue) = apply { dirPartida = value }
    fun setTransportista(value: TransportistaBuilder) = apply { transportista = value }
    fun updateTransportista(value: TransportistaBuilder) = apply {
        transportista = if (transportista == null) { value } else { transportista!! + value }
    }
    fun setDirEstablecimiento(value: TextValue?) = apply { dirEstablecimiento = value }
    fun setContribuyenteEspecial(value: NumericIdentifier? = null) = apply { contribuyenteEspecial = value }
    fun setObligadoLlevarCompt(value: Boolean? = null) = apply { obligadoLlevarCompt = value }
    fun setRise(value: Rise?) = apply { rise = value }

    operator fun plus(other: RemisionBuilder) = merge(other)
    fun merge(other: RemisionBuilder) = apply {
        other.dirPartida?.let { setDirPartida(it) }
        other.transportista?.let { updateTransportista(it) }
        other.dirEstablecimiento?.let { setDirEstablecimiento(it) }
        other.contribuyenteEspecial?.let { setContribuyenteEspecial(it) }
        other.obligadoLlevarCompt?.let { setObligadoLlevarCompt(it) }
        other.rise?.let { setRise(it) }
    }

    override fun validatedBuild() = GuiaRemision.Remision(
        dirPartida!!,
        transportista!!.build(),
        dirEstablecimiento,
        contribuyenteEspecial,
        obligadoLlevarCompt,
        rise
    )
}

