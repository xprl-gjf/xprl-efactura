package uk.co.xprl.efactura.builders.retencion

import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import uk.co.xprl.efactura.*
import uk.co.xprl.efactura.DocSustentoATS.TipoDocSustentoATS
import uk.co.xprl.efactura.builders.liquidacion.ReembolsoDetalleBuilder
import kotlin.test.assertEquals

/**
 * Verify the correct application of verification rules for an
 * implementation of [CompositeBuilder].
 */
internal class SustentoATSBuilderTest {

    /**
     * Verify that, when all builder validation rules for a [SustentoATSBuilder]
     * are satisfied, a call to [SustentoATSBuilder.build] may succeed.
     */
    @Test
    fun validSustentoATSBuilderCanBuild() {
        val sustento = assertDoesNotThrow {
            SustentoATSBuilder()
                .setTipoSustento(SustentoATS.TipoSustento.CREDITO_TRIBUTARIO_IVA)
                .setDocumentoSustento(DocSustentoATSBuilder()
                    .setTipoDocumento(TipoDocSustentoATS.COMPROBANTE_DE_RETENCION)
                    .setFechaEmisionDocSustento(LocalDate.parse("2024-02-26"))
                    .setImpuestos(
                        mapOf(
                            ImpuestoIdentidad(
                                tipoImpuesto = TipoImpuesto.ICE,
                                codigoPorcentaje = 1
                            ) to ImpuestoDetalle(
                                tarifa = UDecimalValue(12.5),
                                baseImponible = UDecimalValue(12.5),
                                valor = UDecimalValue(10.0)
                            )
                        )
                    )
                    .setRetenciones(
                        mapOf(
                            ImpuestoRetencionIva(ImpuestoRetencionIva.IvaRetencionPorcentaje.SETENTA_POR_CIENTO)
                                to ImpuestoRetencionValor(
                                    baseImponible = UDecimalValue(12.5),
                                    porcentajeRetener = UDecimalValue(12.5),
                                    valorRetenido = UDecimalValue(10.0)
                            )
                        )
                    )
                )
                .setTotals(SustentoATS.Totals(
                    totalSinImpuestos = UDecimalValue(0.00),
                    importeTotal = UDecimalValue(0.00)
                ))
                .setPago(PagoRetencionATSBuilder.Residente())
                .setPagos(listOf(
                    PagoATS(formaPago = FormaDePagoATS.CHEQUE_PROPIO, total = UDecimalValue(10.0))
                ))
                .build()
        }

        assertEquals(SustentoATS.TipoSustento.CREDITO_TRIBUTARIO_IVA, sustento.tipoSustento)
    }

    /**
     * Verify that, when all builder validation rules for a [SustentoATSBuilder] that includes reembolsos
     * are satisfied, a call to [SustentoATSBuilder.build] may succeed.
     */
    @Test
    fun validSustentoATSBuilderWithReembolsosCanBuild() {
        val sustento = assertDoesNotThrow {
            SustentoATSBuilder()
                .setTipoSustento(SustentoATS.TipoSustento.CREDITO_TRIBUTARIO_IVA)
                .setDocumentoSustento(
                    DocSustentoATSBuilder()
                    .setTipoDocumento(TipoDocSustentoATS.COMPROBANTE_DE_VENTA_EMITIDO_POR_REEMBOLSO)
                    .setFechaEmisionDocSustento(LocalDate.parse("2024-02-26"))
                    .setImpuestos(
                        mapOf(
                            ImpuestoIdentidad(
                                tipoImpuesto = TipoImpuesto.ICE,
                                codigoPorcentaje = 1
                            ) to ImpuestoDetalle(
                                tarifa = UDecimalValue(12.5),
                                baseImponible = UDecimalValue(12.5),
                                valor = UDecimalValue(10.0)
                            )
                        )
                    )
                    .setRetenciones(
                        mapOf(
                            ImpuestoRetencionIva(ImpuestoRetencionIva.IvaRetencionPorcentaje.SETENTA_POR_CIENTO)
                                    to ImpuestoRetencionValor(
                                baseImponible = UDecimalValue(12.5),
                                porcentajeRetener = UDecimalValue(12.5),
                                valorRetenido = UDecimalValue(10.0)
                            )
                        )
                    )
                    .setReembolsos(ReembolsosATSBuilder()
                        .setTotals(DocSustentoATS.ReembolsoTotals(
                            totalComprobantesReembolso = UDecimalValue(12.5),
                            totalBaseImponibleReembolso = UDecimalValue(12.5),
                            totalImpuestoReembolso = UDecimalValue(0.0)
                        ))
                        .setDetalles(listOf(
                            ReembolsoDetalleBuilder()
                                .setDocReembolso(
                                    DocReembolso(
                                        códigoDocumento = CodeValue.from(1),
                                        códigoEstablecimiento = CodeValue.from(1),
                                        códigoPuntoEmisión = CodeValue.from(1),
                                        secuencial = SecuencialValue.Companion.from(1234),
                                        fechaEmision = LocalDate.parse("2024-02-26"),
                                        numeroAutorizacion = DocAutorizacionValue.from("0123456789")
                                    )
                                )
                                .setProveedor(IdentityValue.ConsumidorFinal)
                                .setTipoProveedorReembolso(TipoProveedorReembolso.PERSONA_NATURAL)
                                .setImpuestos(mapOf(
                                    ImpuestoIdentidad(TipoImpuesto.IVA, codigoPorcentaje = 1)
                                            to ImpuestoDetalle(
                                                baseImponible = UDecimalValue(12.5),
                                                tarifa = UDecimalValue(12.5),
                                                valor = UDecimalValue(10.0)
                                            )
                                    )
                                )
                                .setPaisPago(CodeValue.from(139))
                            )
                        )
                    )
                )
                .setTotals(SustentoATS.Totals(
                    totalSinImpuestos = UDecimalValue(0.00),
                    importeTotal = UDecimalValue(0.00)
                ))
                .setPago(PagoRetencionATSBuilder.Residente())
                .setPagos(listOf(
                    PagoATS(formaPago = FormaDePagoATS.CHEQUE_PROPIO, total = UDecimalValue(10.0))
                ))
                .build()
        }

        assertEquals(SustentoATS.TipoSustento.CREDITO_TRIBUTARIO_IVA, sustento.tipoSustento)
    }


    /**
     * Verify that, when all builder validation rules for a [SustentoATSBuilder] that includes dividendos
     * are satisfied, a call to [SustentoATSBuilder.build] may succeed.
     */
    @Test
    fun validSustentoATSBuilderWithDividendosCanBuild() {
        val sustento = assertDoesNotThrow {
            SustentoATSBuilder()
                .setTipoSustento(SustentoATS.TipoSustento.DISTRIBUCION_DE_DIVIDENDOS)
                .setDocumentoSustento(DocSustentoATSBuilder()
                    .setTipoDocumento(TipoDocSustentoATS.COMPROBANTE_DE_RETENCION)
                    .setFechaEmisionDocSustento(LocalDate.parse("2024-02-26"))
                    .setImpuestos(
                        mapOf(
                            ImpuestoIdentidad(
                                tipoImpuesto = TipoImpuesto.ICE,
                                codigoPorcentaje = 1
                            ) to ImpuestoDetalle(
                                tarifa = UDecimalValue(12.5),
                                baseImponible = UDecimalValue(12.5),
                                valor = UDecimalValue(10.0)
                            )
                        )
                    )
                    .setRetenciones(
                        mapOf(
                            ImpuestoRetencionIva(ImpuestoRetencionIva.IvaRetencionPorcentaje.SETENTA_POR_CIENTO)
                                    to ImpuestoRetencionValor(
                                baseImponible = UDecimalValue(12.5),
                                porcentajeRetener = UDecimalValue(12.5),
                                valorRetenido = UDecimalValue(10.0),
                                dividendos = ImpuestoRetencionValor.DividendoDetalle(
                                    fechaPago = LocalDate.parse("2024-02-26"),
                                    imRentaSoc = UDecimalValue(8.5),
                                    ejerFisUtDiv = 5
                                )
                            )
                        )
                    )
                )
                .setTotals(SustentoATS.Totals(
                    totalSinImpuestos = UDecimalValue(0.00),
                    importeTotal = UDecimalValue(0.00)
                ))
                .setPago(PagoRetencionATSBuilder.Residente())
                .setPagos(listOf(
                    PagoATS(formaPago = FormaDePagoATS.CHEQUE_PROPIO, total = UDecimalValue(10.0))
                ))
                .build()
        }

        assertEquals(SustentoATS.TipoSustento.DISTRIBUCION_DE_DIVIDENDOS, sustento.tipoSustento)
    }
}
