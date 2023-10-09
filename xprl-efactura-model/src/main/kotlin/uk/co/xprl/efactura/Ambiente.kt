package uk.co.xprl.efactura

/**
 * Valores por el campo `ambiente`.
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 4.
 */
enum class Ambiente(val value: Int) {
    PRUEBAS(1),
    PRODUCCIÓN(2)
}