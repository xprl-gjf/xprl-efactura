package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class IdentificationTypeTest {

    @ParameterizedTest(name = "Identification type for {0}")
    @MethodSource("getIdentityValues")
    fun getIdentificationType(value: IdentityValue, expectedIdentificationType: IdentificationType) {
        assertEquals(expectedIdentificationType, value.identificationType)
    }

    companion object {
        @JvmStatic
        private fun getIdentityValues(): List<Arguments> = sequence {
            yield(IdentityValue.RUC.from("0123456789001") to IdentificationType.RUC)
            yield(IdentityValue.Cedula.from("0123456789001") to IdentificationType.CEDULA)
            yield(IdentityValue.Pasaporte.from("012345ABCD") to IdentificationType.PASAPORTE)
            yield(IdentityValue.ConsumidorFinal to IdentificationType.CONSUMIDOR_FINAL)
            yield(IdentityValue.IdentificacionDelExterior.from("0123455") to IdentificationType.EXTERIOR)
        }.asArgs()

        private fun Sequence<Pair<IdentityValue, IdentificationType>>.asArgs(): List<Arguments>
                = this.map { arguments(named(it.first::class.java.simpleName, it.first), it.second) }.toList()
    }
}



