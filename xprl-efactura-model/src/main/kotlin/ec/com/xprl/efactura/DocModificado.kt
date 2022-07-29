package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

data class DocModificado(
    val tipoDocumento: DocumentType,
    val id: DocumentId,
    val fechaEmisionDocSustento: LocalDate
)