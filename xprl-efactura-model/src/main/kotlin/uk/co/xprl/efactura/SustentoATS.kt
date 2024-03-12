package uk.co.xprl.efactura

data class SustentoATS (
    val tipoSustento: TipoSustento,
    val docSustento: DocSustentoATS,
    val pago: PagoRetencionATS,
    val totals: Totals,
    val pagos: List<PagoATS>
) {
    companion object {
        /* empty - included solely to support extensions */
    }

    /**
     * Valores por el campo `codSustento` por Comprobantes de Retencion de ATS
     *
     * Según [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
     * Tabla 5
     */
    enum class TipoSustento(val codigo: Int) {
        CREDITO_TRIBUTARIO_IVA(1),
        GASTO_IR(2),
        ACTIVO_FIJO_CREDITO_TRIBUTARIO_IVA(3),
        ACTIVO_FIJO_GASTO_IR(4),
        LIQUIDACION_GASTO_IR(5),
        INVENTARIO_CREDITO_TRIBUTARIO_IVA(6),
        INVENTARIO_GASTO_IR(7),
        REEMBOLSO_DE_GASTO_INTERMEDIARIO(8),
        REEMBOLSO_POR_SINIESTRO(9),
        DISTRIBUCION_DE_DIVIDENDOS(10),
        CONVENIO_PARA_IFI(11),
        IMPUESTO_RETENCION_PRESUNTIVO(12),
        VALOR_FAVOR_DE_SUJECTO_PASIVO(13),
        VALOR_POR_OPERADOR_DE_TRANSPORTE(14),
        PAGO_POR_CONSUMO_PROPIO(15),
        CASO_ESPECIAL(0);

        companion object {
            /* empty - included solely to support extensions */
        }
    }

    data class Totals(
        val totalSinImpuestos: UDecimalValue,
        val importeTotal: UDecimalValue
    )
}