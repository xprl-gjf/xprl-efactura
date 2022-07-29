package ec.com.xprl.efactura

/**
 * Valores por el campo `codDoc`.
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 3.
 */
enum class DocumentType(val value: Int) {
    FACTURA(1),
    LIQUIDACION_DE_COMPRA(3),
    NOTA_DE_CREDITO(4),
    NOTA_DE_DEBITO(5),
    GUIA_DE_REMISION(6),
    COMPROBANTE_DE_RETENCION(7);
}

/**
 * Extension property on [ComprobanteElectronico] to return the corresponding [DocumentType]
 */
val ComprobanteElectronico.documentType: DocumentType
    get() = when(this) {
        is Factura -> DocumentType.FACTURA
        is NotaCredito -> DocumentType.NOTA_DE_CREDITO
        is NotaDebito -> DocumentType.NOTA_DE_DEBITO
        is ComprobanteRetencion -> DocumentType.COMPROBANTE_DE_RETENCION
        is GuiaRemision -> DocumentType.GUIA_DE_REMISION
        is LiquidacionCompra -> DocumentType.LIQUIDACION_DE_COMPRA
        // occasionally when performing an incremental build, the compiler raises an error
        // that this 'when' clause is not exhaustive.
        // However, it seems to be possible to resolve it by performing a clean build.
        // else -> throw IllegalArgumentException("Unsupported comprobante electronico document type: ${this::class.java}")
    }
