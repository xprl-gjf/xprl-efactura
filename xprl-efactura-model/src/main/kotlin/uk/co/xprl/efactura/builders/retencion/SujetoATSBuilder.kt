package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresIf

/**
 * Mutable builder for [ComprobanteRetencionATS.Sujeto].
 */
class SujetoATSBuilder : AbstractBuilder<SujetoATSBuilder, ComprobanteRetencionATS.Sujeto>(
    ComprobanteRetencionATS.Sujeto::class.java,
    requires("identificación") { it.identificacion },
    requiresIf({ it.identificacion?.identificationType == IdentificationType.EXTERIOR }, "tipoSujetoRetenido") { it.tipoSujetoRetenido },
    requires("razónSocial") { it.razonSocial },
    requires("parteRelacionado") { it.parteRelacionado }
) {
    private var tipoSujetoRetenido: TipoSujetoRetenido? = null
    private var identificacion: IdentityValue? = null
    private var razonSocial: TextValue? = null
    private var parteRelacionado: Boolean? = null

    fun setTipoSujetoRetenido(value: TipoSujetoRetenido) = apply { tipoSujetoRetenido = value }
    fun setIdentificacion(value: IdentityValue) = apply { identificacion = value }
    fun setRazonSocial(value: TextValue) = apply { razonSocial = value }
    fun setParteRelacionado(value: Boolean) = apply { parteRelacionado = value }

    operator fun plus(other: SujetoATSBuilder) = merge(other)
    fun merge(other: SujetoATSBuilder) = apply {
        other.tipoSujetoRetenido?.let { setTipoSujetoRetenido(it) }
        other.identificacion?.let { setIdentificacion(it) }
        other.razonSocial?.let { setRazonSocial(it) }
        other.parteRelacionado?.let { setParteRelacionado(it) }
    }

    override fun validatedBuild() = ComprobanteRetencionATS.Sujeto(
        tipoSujetoRetenido,
        identificacion!!,
        razonSocial!!,
        parteRelacionado!!
    )
}