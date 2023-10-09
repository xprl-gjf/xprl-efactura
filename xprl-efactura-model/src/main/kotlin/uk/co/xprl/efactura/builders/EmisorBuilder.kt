package uk.co.xprl.efactura.builders

import uk.co.xprl.efactura.*

/**
 * Mutable builder for an [Emisor].
 */
class EmisorBuilder: AbstractBuilder<EmisorBuilder, Emisor>(
    Emisor::class.java,
    requires("RUC") { it.RUC },
    requires("razónSocial") { it.razónSocial },
    requires("direccionMatriz") { it.direccionMatriz },
    requires("códigoEstablecimiento") { it.códigoEstablecimiento },
    requires("códigoPuntoEmisión") { it.códigoPuntoEmisión }
) {
    private var RUC: IdentityValue.RUC? = null
    private var razónSocial: TextValue? = null
    private var direccionMatriz: TextValue? = null
    private var códigoEstablecimiento: CodeValue? = null
    private var códigoPuntoEmisión: CodeValue? = null
    private var direcciónEstablecimiento: TextValue? = null
    private var nombreComercial: TextValue? = null
    private var contribuyenteEspecial: NumericIdentifier? = null
    private var obligadoLlevarCompt: Boolean? = null

    fun setRUC(value: IdentityValue.RUC) = apply { RUC = value }
    fun setRazonSocial(value: TextValue) = apply { razónSocial = value }
    fun setDireccionMatriz(value: TextValue) = apply { direccionMatriz = value }
    fun setCodigoEstablecimiento(value: CodeValue) = apply { códigoEstablecimiento = value }
    fun setCodigoPuntoEmision(value: CodeValue) = apply { códigoPuntoEmisión = value }
    fun setDireccionEstablecimiento(value: TextValue? = null) = apply { direcciónEstablecimiento = value }
    fun setNombreComercial(value: TextValue? = null) = apply { nombreComercial = value }
    fun setContribuyenteEspecial(value: NumericIdentifier? = null) = apply { contribuyenteEspecial = value }
    fun setObligadoLlevarCompt(value: Boolean? = null) = apply { obligadoLlevarCompt = value }

    operator fun plus(other: EmisorBuilder) = merge(other)
    fun merge(other: EmisorBuilder) = apply {
        other.RUC?.let { setRUC(it) }
        other.razónSocial?.let { setRazonSocial(it) }
        other.direccionMatriz?.let { setRazonSocial(it) }
        other.códigoEstablecimiento?.let { setCodigoEstablecimiento(it) }
        other.códigoPuntoEmisión?.let { setCodigoPuntoEmision(it) }
        other.direcciónEstablecimiento?.let { setDireccionEstablecimiento(it) }
        other.nombreComercial?.let { setNombreComercial(it) }
        other.contribuyenteEspecial?.let { setContribuyenteEspecial(it) }
        other.obligadoLlevarCompt?.let { setObligadoLlevarCompt(it) }
    }

    override fun validatedBuild() = Emisor(
        RUC!!,
        razónSocial!!,
        direccionMatriz!!,
        códigoEstablecimiento!!,
        códigoPuntoEmisión!!,
        direcciónEstablecimiento,
        nombreComercial,
        contribuyenteEspecial,
        obligadoLlevarCompt
    )
}