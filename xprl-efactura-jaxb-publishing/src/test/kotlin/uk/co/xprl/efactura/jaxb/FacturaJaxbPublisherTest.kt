package uk.co.xprl.efactura.jaxb

import uk.co.xprl.efactura.*
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

internal class FacturaJaxbPublisherTest {

    @ParameterizedTest(name = "publish populates factura and schemaVersion for schemaVersion {0}")
    @MethodSource("getSchemaVersions")
    fun publishPopulatesSchemaVersion(schemaVersion: SchemaVersion<Factura>) {
        val factura = FacturaBuilder().build()
        val publishedEfactura = JaxbComprobantePublisher<Factura>(defaultAmbiente, validationEnabled = true)
            .publish(factura, schemaVersion)

        val expected = PublishedComprobanteBuilder(factura, schemaVersion)
            .build()

        assertEquals(expected.comprobante, publishedEfactura.comprobante)
        assertEquals(expected.schemaVersion, publishedEfactura.schemaVersion)
    }


    @ParameterizedTest(name = "publish populates ambiente metadata for ambiente {0}")
    @ValueSource(strings = ["PRUEBAS", "PRODUCCIÓN"])
    fun publishPopulatesAmbiente(ambienteStr: String) {
        val factura = FacturaBuilder().build()
        val ambiente = Ambiente.valueOf(ambienteStr)
        val schemaVersion = SchemaVersion.FacturaV100
        val publishedEfactura = JaxbComprobantePublisher<Factura>(ambiente, validationEnabled = false)
            .publish(factura, schemaVersion)

        val expected = PublishedComprobanteBuilder(factura, schemaVersion)
            .setAmbiente(ambiente)
            .build()

        assertEquals(expected.comprobante, publishedEfactura.comprobante)
        assertEquals(expected.tipoEmisión, publishedEfactura.tipoEmisión)
        assertEquals(expected.ambiente, publishedEfactura.ambiente)
    }

    @Test
    fun publishPopulatesClaveAcceso() {
        val factura = FacturaBuilder().build()

        val ambiente = defaultAmbiente
        val mockClaveAcceso = "9".repeat(49)
        val jaxbFactory = JaxbComprobanteFactoryImpl(ambiente, TipoEmisión.NORMAL) { _, _, _ ->
            mockClaveAcceso
        }

        val publishedEfactura = JaxbComprobantePublisher<Factura>(ambiente, factory = jaxbFactory, validationEnabled = false)
            .publish(factura, SchemaVersion.FacturaV100)

        val expected = PublishedComprobanteBuilder(factura, SchemaVersion.FacturaV100)
            .setClaveAcceso(mockClaveAcceso)
            .build()

        assertEquals(expected.comprobante, publishedEfactura.comprobante)
        assertEquals(expected.claveAcceso, publishedEfactura.claveAcceso)
    }

    companion object {
        @JvmStatic
        private fun getSchemaVersions(): List<Arguments> = sequence {
            yield(SchemaVersion.FacturaV100)
            yield(SchemaVersion.FacturaV110)
        }.map {
            arguments(it)
        }.toList()
    }
}


private class FacturaBuilder {
    private var emisor = defaultEmisor
    private var comprador = defaultComprador
    private var totals = defaultTotals
    private var pagos = defaultPagos
    private var detalles = defaultDetalles

    fun build() = Factura(
        defaultSecuencial,
        defaultFechaEmision,
        emisor,
        comprador,
        Factura.Valores(totals, pagos),
        detalles
    )
}

@Suppress("unused")
private class PublishedComprobanteBuilder<T: ComprobanteElectronico>(val comprobante: T, val schemaVersion: SchemaVersion<T>) {
    private var ambiente = defaultAmbiente
    private var tipoEmisión = TipoEmisión.NORMAL
    private var claveAcceso: ClaveAcceso = "IGNORED"
    private var xml = "IGNORED"

    fun setAmbiente(ambiente: Ambiente): PublishedComprobanteBuilder<T> {
        this.ambiente = ambiente; return this
    }
    fun setTipoEmisión(tipoEmisión: TipoEmisión): PublishedComprobanteBuilder<T> {
        this.tipoEmisión = tipoEmisión; return this
    }
    fun setClaveAcceso(claveAcceso: ClaveAcceso): PublishedComprobanteBuilder<T> {
        this.claveAcceso = claveAcceso; return this
    }
    fun setXml(xml: String): PublishedComprobanteBuilder<T> {
        this.xml = xml; return this
    }
    fun build() = PublishedComprobante(
        comprobante, schemaVersion, ambiente, tipoEmisión, claveAcceso, xml
    )
}

private val defaultSecuencial = SecuencialValue.from(1)
private val defaultFechaEmision = LocalDate(2022, 1, 1)
private val defaultAmbiente = Ambiente.PRUEBAS
private val defaultEmisor = Emisor(
    RUC = IdentityValue.RUC.from("0123456789001"),
    razónSocial = TextValue.from("PRUEBAS SERVICIO DE RENTAS INTERNAS"),
    direccionMatriz = TextValue.from("DIRECCION MATRIZ"),
    códigoEstablecimiento = CodeValue.from(1),
    códigoPuntoEmisión = CodeValue.from(1)
)
private val defaultComprador = Comprador(
    identificación = IdentityValue.RUC.from("0123456789001"),
    razónSocial = TextValue.from("PRUEBAS SERVICIO DE RENTAS INTERNAS")
)
private val defaultTotalImpuestos = mapOf(
    ImpuestoIdentidad(
        tipoImpuesto = TipoImpuesto.ICE,
        codigoPorcentaje = 1
    ) to ImpuestoTotal(
        baseImponible = UDecimalValue(12.5),
        valor = UDecimalValue(10.0)
    )
)
private val defaultTotals = Factura.Totals(
    totalSinImpuestos = UDecimalValue(0),
    totalDescuento = UDecimalValue(0),
    totalConImpuestos = defaultTotalImpuestos,
    importeTotal = UDecimalValue(0)
)
private val defaultPagos = listOf(
    Pago(formaPago = FormaDePago.TARJETA_DE_DEBITO, total = UDecimalValue(1.0))
)
private val defaultImpuestos = mapOf(
    ImpuestoIdentidad(
        tipoImpuesto = TipoImpuesto.ICE,
        codigoPorcentaje = 1
    ) to ImpuestoDetalle(
        tarifa = UDecimalValue(12.5),
        baseImponible = UDecimalValue(12.5),
        valor = UDecimalValue(10.0)
    )
)
private val defaultDetalles = listOf(
    ComprobanteDetalle(
        codigoPrincipal = AlphanumericCodeValue.from("ABC-123"),
        descripcion = TextValue.from("FACTURA DETALLE"),
        cantidad = UDecimalValue(1),
        precioUnitario = UDecimalValue(99.9),
        descuento = UDecimalValue(0),
        precioTotalSinImpuesto = UDecimalValue(99.9),
        impuestos = defaultImpuestos
    )
)
