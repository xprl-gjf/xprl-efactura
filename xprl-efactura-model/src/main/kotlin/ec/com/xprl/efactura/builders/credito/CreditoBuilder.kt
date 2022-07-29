package ec.com.xprl.efactura.builders.credito

import ec.com.xprl.efactura.NotaCredito
import ec.com.xprl.efactura.Rise
import ec.com.xprl.efactura.TextValue
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.DocModificadoBuilder
import ec.com.xprl.efactura.builders.requires

class CreditoBuilder: CompositeBuilder<CreditoBuilder, NotaCredito.Credito>(
    NotaCredito.Credito::class.java,
    innerBuilderProperties = listOf(
        { it.documentoModificado },
        { it.valores }
    ),
    requires("documentoModificado") { it.documentoModificado },
    requires("motivo") { it.motivo },
    requires("valores") { it.valores }
) {
    private var documentoModificado: DocModificadoBuilder? = null
    private var motivo: TextValue? = null
    private var valores: ValoresBuilder? = null
    private var rise: Rise? = null

    fun setDocumentoModificado(value: DocModificadoBuilder) = apply { documentoModificado = value }
    fun updateDocumentoModificado(value: DocModificadoBuilder) = apply {
        documentoModificado = if (documentoModificado == null) { value } else { documentoModificado!! + value }
    }
    fun setMotivo(value: TextValue) = apply { motivo = value }
    fun setValores(value: ValoresBuilder) = apply { valores = value }
    fun updateValores(value: ValoresBuilder) = apply {
        valores = if (valores == null) { value } else { valores!! + value }
    }
    fun setRise(value: Rise? = null) = apply { rise = value }

    operator fun plus(other: CreditoBuilder) = merge(other)
    fun merge(other: CreditoBuilder) = apply {
        other.rise?.let { setRise(it) }
        other.documentoModificado?.let { updateDocumentoModificado(it) }
        other.motivo?.let { setMotivo(it) }
        other.valores?.let { updateValores(it) }
    }

    override fun validatedBuild() = NotaCredito.Credito(
        documentoModificado!!.build(),
        motivo!!,
        valores!!.build(),
        rise
    )
}