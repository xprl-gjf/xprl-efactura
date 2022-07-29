package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal

internal class UDecimalValueTest {
    /**
     * Verify that an attempt to create a [UDecimalValue] from
     * an invalid value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidUDecimalValueException(value: BigDecimal) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            UDecimalValue.from(value)
        }
    }


    /**
     * Verify that a [UDecimalValue] can be created from a
     * valid BigDecimal value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validUDecimalValue(value: BigDecimal) {
        val result = UDecimalValue.from(value)
        Assertions.assertEquals(value, result.value)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            BigDecimal(-1),  // negative value
            BigDecimal(-0.0001)
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            BigDecimal(0),
            BigDecimal(0.5),
            BigDecimal(100),
            BigDecimal(1000.123456)
        ).asArgs()
    }
}
