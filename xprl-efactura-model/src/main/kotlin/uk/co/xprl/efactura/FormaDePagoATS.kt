package uk.co.xprl.efactura

/**
 * Valores por el campo `formaPago` por Comprobantes de Retencion de ATS.
 *
 * Según [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
 * Tabla 13
 */
enum class FormaDePagoATS(val value: Int) {
    SIN_SISTEMA_FINANCIERO(1),
    CHEQUE_PROPIO(2),
    CHEQUE_CERTIFICADO(3),
    CHEQUE_DE_GERENCIA(4),
    CHEQUE_DEL_EXTERIOR(5),
    DEBITO_DE_CUENTA(6),
    TRANSFERENCIA_PROPIO_BANCO(7),
    TRANSFERENCIA_OTRO_BANCO_NACIONAL(8),
    TRANSFERENCIA_BANCO_EXTERIOR(9),
    TARJETA_DE_CREDITO_NACIONAL(10),
    TARJETA_DE_CREDITO_INTERNACIONAL(11),
    GIRO(12),
    DEPOSITO_EN_CUENTA(13),
    ENDOSO_DE_INVERSION(14),
    COMPENSACION_DE_DEUDAS(15),
    TARJETA_DE_DEBITO(16),
    DINERO_ELECTRONICO(17),
    TARJETA_PREPAGO(18),
    TARJETA_DE_CREDITO(19),
    OTROS_CON_SISTEMA_FINANCIERO(20),
    ENDOSO_DE_TITULOS(21);

    companion object {
        /* empty - included solely to support extensions */
    }
}