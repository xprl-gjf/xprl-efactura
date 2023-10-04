package uk.co.xprl.efactura

sealed class PagoRetencionATS private constructor(
    val tipoPago : TipoPagoRetencion,
    val pagoRegFis : Boolean?
) {

    companion object {
        /* empty - included solely to support extensions */
    }

    class Residente : PagoRetencionATS(TipoPagoRetencion.RESIDENTE, null)

    class NoResidente(
        val tipoRegimen: TipoRegimenFiscalDelExterior,
        val pais: PaisCodeValue,
        val aplicConvDobTrib: Boolean,
        val pagExtSujRetNorLeg: Boolean?
    ) : PagoRetencionATS(TipoPagoRetencion.NO_RESIDENTE, true)
}

/**
 * Valores por el campo `pagoLocExt` por Comprobantes de Retencion de ATS.
 *
 * Según [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
 * Tabla 15
 */
enum class TipoPagoRetencion(val codigo: Int) {
    RESIDENTE(1),
    NO_RESIDENTE(2);

    companion object {
        /* empty - included solely to support extensions */
    }
}


/**
 * Valores por el campo `tipoRegi` por Comprobantes de Retencion de ATS
 * cuando 'pagoLocExt' es igual de `NO_RESIDENTE`.
 *
 * Según [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
 * Tabla 19
 */
enum class TipoRegimenFiscalDelExterior(val codigo: Int) {
    REGIMEN_GENERAL(1),
    PARAISO_FISCAL(2),
    REGIMEN_FISCAL_PREFERENTE(3);

    companion object {
        /* empty - included solely to support extensions */
    }
}
