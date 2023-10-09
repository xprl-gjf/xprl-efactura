package uk.co.xprl.efactura

import uk.co.xprl.efactura.test.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ComprobanteElectronicoDocumentTypeTest {

    @ParameterizedTest(name = "Document type for {0}")
    @MethodSource("getComprobantesElectronicos")
    fun getDocumentType(value: ComprobanteElectronico, expectedDocumentType: DocumentType) {
        Assertions.assertEquals(expectedDocumentType, value.documentType)
    }

    companion object {
        @JvmStatic
        private fun getComprobantesElectronicos(): List<Arguments> = sequence {
            yield(createFactura() to DocumentType.FACTURA)
        }.asArgs()

        private fun Sequence<Pair<ComprobanteElectronico, DocumentType>>.asArgs(): List<Arguments>
                = this.map { Arguments.arguments(Named.named(it.first::class.java.simpleName, it.first), it.second) }.toList()
    }
}


private fun createFactura() = Factura(
    defaultSecuencial,
    defaultFechaEmision,
    defaultEmisor,
    defaultComprador,
    Factura.Valores(defaultTotals, defaultPagos),
    defaultDetalles
)
