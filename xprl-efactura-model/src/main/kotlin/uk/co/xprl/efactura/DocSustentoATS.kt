package uk.co.xprl.efactura

import kotlinx.datetime.LocalDate

class DocSustentoATS(
    val tipoDocumento: DocSustentoATS.TipoDocSustentoATS,
    val numDocSustento: DocumentId? = null,
    val fechaEmisionDocSustento: LocalDate,
    val fechaRegistroContable: LocalDate? = null,
    // number, 10 or 37 or 49 digits
    val numAutDocSustento: DocAutorizacionValue? = null,
    val impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>,
    val retenciones: Map<ImpuestoRetencionIdentidad, ImpuestoRetencionValor>,
    val reembolsos: ReembolsoATS? = null
) {

    companion object {
        /* empty - included solely to support extensions */
    }

    /**
     * Valores por el campo `codDocSustento` por Comprobantes de Retencion de ATS
     *
     * Según [Catalogo ATS](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/e6a826af-b22c-40bb-8752-d711f293b8f9/Catalogo_ATS.xls),
     * Tabla 4
     */
    enum class TipoDocSustentoATS(val codigo: Int) {
        FACTURA(1),
        NOTA_O_BOLETA_DE_VENTA(2),
        LIQUIDACION_DE_COMPRA_DE_BIENES_O_PRESTACION_DE_SERVICIOS(3),
        NOTA_DE_CREDITO(4),
        NOTA_DE_DEBITO(5),
        GUIA_DE_REMISION(6),
        COMPROBANTE_DE_RETENCION(7),
        BOLETO_POR_ESPECTACULO_PUBLICO(8),
        TIQUETE_EMITIDO_POR_MAQUINA_REGISTRADORA(9),
        PASAJE_EXPEDIDO_POR_EMPRESA_DE_AVIACION(11),
        DOCUMENTO_EMITIDO_POR_INSTITUCION_FINANCIERA(12),
        COMPROBANTE_DE_VENTA_EMITIDO_EN_EL_EXTERIOR(15),
        FUE_DAO_O_DAV(16),
        DOCUMENTO_AUTORIZADO_UTILIZADO_EN_VENTAS(18),
        COMPROBANTE_DE_PAGO_DE_CUOTAS_O_APORTES(19),
        DOCUMENTO_POR_SERVICIOS_ADMINISTRATIVOS_POR_INST_DEL_ESTADO(20),
        CARTA_DE_PORTE_AEREO(21),
        RECAP(22),
        NOTA_DE_CREDITO_TC(23),
        NOTA_DE_DEBITO_TC(24),
        COMPROBANTE_DE_VENTA_EMITIDO_POR_REEMBOLSO(41),
        DOCUMENTO_RETENCION_PRESUNTIVA(42),
        LIQUIDACION_PARA_EXPLOTACION_DE_HIDROCARBUROS(43),
        COMPROBANTE_DE_CONTRIBUCIONES_Y_APORTES(44),
        LIQUIDACION_POR_RECLAMOS_DE_ASEGURADORAS(45),
        NOTA_DE_CREDITO_POR_REEMBOLSO_POR_INTERMEDIARIO(47),
        NOTA_DE_DEBITO_POR_REEMBOLSO_POR_INTERMEDIARIO(48),
        PROVEEDOR_DIRECTO_DE_EXPORTADOR_BAJO_REGIMEN_ESPECIAL(49),
        EMPREZA_PUBLICA_QUE_PERCIBE_INGRESO_EXENTO_DE_IR(50),
        NOTA_CREDITO_EMPREZA_PUBLICA_QUE_PERCIBE_INGRESO_EXENTO_DE_IR(51),
        NOTA_DEBITO_EMPREZA_PUBLICA_QUE_PERCIBE_INGRESO_EXENTO_DE_IR(52),
        LIQUIDACION_DE_BIENES_MUEBLES_USADOS(294),
        LIQUIDACION_DE_VEHICULOS_USADOS(344),
        ACTA_ENTREGA_RECEPCION_PET(364),
        FACTURA_OPERADORA_TRANSPORTE(370),
        COMPROBANTE_OPERADORA_TRANSPORTE(371),
        NOTA_CREDITO_OPERADORA_TRANSPORTE(372),
        NOTA_DEBITO_OPERADORA_TRANSPORTE(373),
        LIQUIDACION_OPERADORA_TRANSPORTE(374),
        LIQUIDACION_RISE_BIENES_O_PRESTACION_DE_SERVICIOS(375);

        companion object {
            /* empty - included solely to support extensions */
        }
    }

    data class ReembolsoATS(
        val totals: ReembolsoTotals,
        val detalles: List<ReembolsoDetalle>
    )

    data class ReembolsoTotals(
        val totalComprobantesReembolso: UDecimalValue,
        val totalBaseImponibleReembolso: UDecimalValue,
        val totalImpuestoReembolso: UDecimalValue,
    )
}

