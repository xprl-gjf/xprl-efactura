package uk.co.xprl.efactura

/**
 * Valores por el campo <tt>tipoIdentificaciónComprador</tt>.
 *
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 6.
 */
enum class IdentificationType(val value: Int) {
    RUC(4),
    CEDULA(5),
    PASAPORTE(6),
    CONSUMIDOR_FINAL(7),
    EXTERIOR(8);
}

/**
 * Extension property on [IdentityValue] to return the corresponding [IdentificationType]
 */
val IdentityValue.identificationType: IdentificationType
    get() = when(this) {
        is IdentityValue.RUC -> IdentificationType.RUC
        is IdentityValue.Cedula -> IdentificationType.CEDULA
        is IdentityValue.Pasaporte -> IdentificationType.PASAPORTE
        is IdentityValue.ConsumidorFinal -> IdentificationType.CONSUMIDOR_FINAL
        is IdentityValue.IdentificacionDelExterior -> IdentificationType.EXTERIOR
    }
