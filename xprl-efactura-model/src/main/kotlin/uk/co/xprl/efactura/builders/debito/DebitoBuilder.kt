package uk.co.xprl.efactura.builders.debito

import uk.co.xprl.efactura.NotaDebito
import uk.co.xprl.efactura.Pago
import uk.co.xprl.efactura.Rise
import uk.co.xprl.efactura.builders.CompositeBuilder
import uk.co.xprl.efactura.builders.DocModificadoBuilder
import uk.co.xprl.efactura.builders.requires
import uk.co.xprl.efactura.builders.requiresNotEmpty

class DebitoBuilder: CompositeBuilder<DebitoBuilder, NotaDebito.Debito>(
    NotaDebito.Debito::class.java,
    innerBuilderProperties = listOf(
        { it.documentoModificado },
        { it.valores }
    ),
    requires("documentoModificado") { it.documentoModificado },
    requires("valores") { it.valores },
    requiresNotEmpty("pagos") { it.pagos },
    requiresNotEmpty("motivos") { it.motivos },
) {
    private var documentoModificado: DocModificadoBuilder? = null
    private var valores: ValoresBuilder? = null
    private var pagos: List<Pago>? = null
    private var motivos: List<NotaDebito.Motivo>? = null
    private var rise: Rise? = null

    fun setDocumentoModificado(value: DocModificadoBuilder) = apply { documentoModificado = value }
    fun updateDocumentoModificado(value: DocModificadoBuilder) = apply {
        documentoModificado = if (documentoModificado == null) { value } else { documentoModificado!! + value }
    }
    fun setValores(value: ValoresBuilder) = apply { valores = value }
    fun updateValores(value: ValoresBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }
    fun setPagos(value: List<Pago>) = apply { pagos = value }
    fun updatePagos(value: List<Pago>) = apply {
        pagos = if (pagos == null) { value } else { pagos!! + value }
    }
    fun setMotivos(value: List<NotaDebito.Motivo>) = apply { motivos = value }
    fun updateMotivos(value: List<NotaDebito.Motivo>) = apply {
        motivos = if (motivos == null) { value } else { motivos!! + value }
    }
    fun setRise(value: Rise? = null) = apply { rise = value }

    operator fun plus(other: DebitoBuilder) = merge(other)
    fun merge(other: DebitoBuilder) = apply {
        other.documentoModificado?.let { updateDocumentoModificado(it) }
        other.valores?.let { updateValores(it) }
        other.pagos?.let { updatePagos(it) }
        other.motivos?.let { updateMotivos(it) }
        other.rise?.let { setRise(it) }
    }

    override fun validatedBuild() = NotaDebito.Debito(
        documentoModificado!!.build(),
        valores!!.build(),
        pagos!!,
        motivos!!,
        rise
    )
}