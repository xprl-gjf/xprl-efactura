package uk.co.xprl.efactura.builders.retencion

import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.builders.*
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty
import kotlinx.datetime.LocalDate

/**
 * Mutable builder for [ComprobanteRetencion.Valores].
 */
class ValoresATSBuilder: CompositeBuilder<ValoresATSBuilder, ComprobanteRetencionATS.Valores>(
    ComprobanteRetencionATS.Valores::class.java,
    innerBuilderProperties = { builder ->
        builder.sustentos ?: emptyList()
    },
    requires("periodoFiscal") { it.periodoFiscal },
    requiresNotEmpty("sustentos") { it.sustentos }
) {
    private var periodoFiscal: LocalDate? = null
    private var sustentos: List<SustentoATSBuilder>? = null

    fun setPeriodoFiscal(value: LocalDate) = apply { periodoFiscal = value }
    fun setSustenos(value: List<SustentoATSBuilder>) = apply { sustentos = value }
    fun updateSustentos(value: List<SustentoATSBuilder>) = apply {
        sustentos = if (sustentos == null) { value } else { sustentos!! + value }
    }

    operator fun plus(other: ValoresATSBuilder) = merge(other)
    fun merge(other: ValoresATSBuilder) = apply {
        other.periodoFiscal?.let { setPeriodoFiscal(it) }
        other.sustentos?.let { updateSustentos(it) }
    }

    override fun validatedBuild() = ComprobanteRetencionATS.Valores(
        periodoFiscal!!,
        sustentos!!.map { it.build() },
    )
}
