package uk.co.xprl.efactura

import kotlinx.datetime.LocalDate

data class DocReembolso(
    val códigoDocumento: CodeValue,
    val códigoEstablecimiento: CodeValue,
    val códigoPuntoEmisión: CodeValue,
    val secuencial: SecuencialValue,
    val fechaEmision: LocalDate,
    val numeroAutorizacion: DocAutorizacionValue
)