package uk.co.xprl.efactura

/**
 * Valores por el campo <tt>tipoProveedorReembolso</tt>.
 *
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 26.
 */
enum class TipoProveedorReembolso(val value: Int) {
    PERSONA_NATURAL(1),
    SOCIEDAD(2);
}