package uk.co.xprl.efactura

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MonedaTest {

    /**
     * Verify that the default value of [Moneda] is "DOLAR"
     */
    @Test
    fun defaultMonedaIsDolar() {
        assertEquals("DOLAR", Moneda.default.value)
    }

    /**
     * Verify the value of [Moneda.DOLAR]
     */
    @Test
    fun monedaDolarValue() {
        assertEquals("DOLAR", Moneda.DOLAR.value)
    }


    /**
     * Verify that an attempt to create an [Moneda] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidMonedaException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            Moneda.from(value)
        }
    }

    /**
     * Verify that an [Moneda] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validMoneda(value: String) {
        val result = Moneda.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that the Moneda MAX_LENGTH property returns the expected value
     */
    @Test
    fun monedaMaxLength() {
        kotlin.test.assertEquals(15, Moneda.MAX_LENGTH)
    }

    /**
     * Verify that [Moneda] equality uses value comparison and not reference comparison
     */
    @ParameterizedTest
    @MethodSource("getValidValues")
    fun monedaValueEquality(value: String) {
        val result1 = Moneda.from(value)
        val result2 = Moneda.from(value)
        assertNotSame(result2, result1)
        assertEquals(result2, result1)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "X".repeat(16),  // value too long
            "ABC\nXYZ",     // newline
            "ABC&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "X".repeat(15),
            "ABC xyz",
        ).asArgs()
    }
}
