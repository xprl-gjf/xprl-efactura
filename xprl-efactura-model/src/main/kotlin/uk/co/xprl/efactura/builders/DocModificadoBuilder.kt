package uk.co.xprl.efactura.builders

import uk.co.xprl.efactura.DocModificado
import uk.co.xprl.efactura.DocumentId
import uk.co.xprl.efactura.DocumentType
import kotlinx.datetime.LocalDate

class DocModificadoBuilder: AbstractBuilder<DocModificadoBuilder, DocModificado>(
    DocModificado::class.java,
    requires("tipoDocumento") { it.tipoDocumento },
    requires("id") { it.id },
    requires("fechaEmisionDocSustento") { it.fechaEmisionDocSustento }
) {
    private var tipoDocumento: DocumentType? = null
    private var id: DocumentId? = null
    private var fechaEmisionDocSustento: LocalDate? = null

    fun setTipoDocumento(value: DocumentType) = apply { tipoDocumento = value }
    fun setId(value: DocumentId) = apply { id = value }
    fun setFechaEmisionDocSustento(value: LocalDate) = apply { fechaEmisionDocSustento = value }

    operator fun plus(other: DocModificadoBuilder) = merge(other)
    fun merge(other: DocModificadoBuilder) = apply {
        other.tipoDocumento?.let { setTipoDocumento(it) }
        other.id?.let { setId(it) }
        other.fechaEmisionDocSustento?.let { setFechaEmisionDocSustento(it) }
    }

    override fun validatedBuild() = DocModificado(
        tipoDocumento!!,
        id!!,
        fechaEmisionDocSustento!!
    )
}