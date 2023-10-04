package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.PagoRetencionATS
import uk.co.xprl.efactura.PaisCodeValue
import uk.co.xprl.efactura.TipoRegimenFiscalDelExterior
import uk.co.xprl.efactura.builders.AbstractBuilder
import uk.co.xprl.efactura.builders.BuilderValidationRule
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresIf

sealed class PagoRetencionATSBuilder<TBuilder: PagoRetencionATSBuilder<TBuilder>>(
    vararg validationRules: BuilderValidationRule<TBuilder>
) : AbstractBuilder<TBuilder, PagoRetencionATS>(
    PagoRetencionATS::class.java,
    *validationRules
) {
    class Residente : PagoRetencionATSBuilder<Residente>() {
        override fun validatedBuild() = PagoRetencionATS.Residente()
    }

    class NoResidente : PagoRetencionATSBuilder<NoResidente>(
        requires("tipoRegimen") { it.tipoRegimen },
        requires("pais") { it.pais },
        requires("aplicConvDobTrib") { it.aplicConvDobTrib },
        requiresIf({ it.aplicConvDobTrib == false }, "pagExtSujRetNorLeg") { it.pagExtSujRetNorLeg }
    ) {
        private var tipoRegimen: TipoRegimenFiscalDelExterior? = null
        private var pais: PaisCodeValue? = null
        private var aplicConvDobTrib: Boolean? = null
        private var pagExtSujRetNorLeg: Boolean? = null

        fun setTipoRegimen(value: TipoRegimenFiscalDelExterior) = apply { tipoRegimen = value }
        fun setPais(value: PaisCodeValue) = apply { pais = value }
        fun setAplicConvDobTrib(value: Boolean) = apply { aplicConvDobTrib = value }
        fun setPagExtSujRetNorLeg(value: Boolean) = apply { pagExtSujRetNorLeg = value }

        operator fun plus(other: NoResidente) = merge(other)
        fun merge(other: NoResidente) = apply {
            other.tipoRegimen?.let { setTipoRegimen(it) }
            other.pais?.let { setPais(it) }
            other.aplicConvDobTrib?.let { setAplicConvDobTrib(it) }
            other.pagExtSujRetNorLeg?.let { setPagExtSujRetNorLeg(it) }
        }

        override fun validatedBuild() = PagoRetencionATS.NoResidente(
            tipoRegimen!!,
            pais!!,
            aplicConvDobTrib!!,
            pagExtSujRetNorLeg
        )
    }

    companion object {
        /* empty - included solely to support extensions */
    }
}