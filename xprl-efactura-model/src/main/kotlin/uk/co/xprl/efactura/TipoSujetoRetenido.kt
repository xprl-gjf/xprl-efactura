package uk.co.xprl.efactura

/**
 * Valores por el campo `tipoSujetoRetenido` en una ComprobanteRetencionATS.
 *
 * Según el [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
 * Tabla 14.
 */
enum class TipoSujetoRetenido(val value: Int) {
    PERSONA_NATURAL(1),
    SOCIEDAD(2);

    companion object {
        /* empty - included solely to support extensions */
    }
}