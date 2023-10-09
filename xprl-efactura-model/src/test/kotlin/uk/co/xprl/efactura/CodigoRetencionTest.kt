package uk.co.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CodigoRetencionTest {

    /**
     * Verify that an attempt to create an [CodigoRetencion] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidCodigoRetencionValueException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            CodigoRetencion.from(value)
        }
    }

    /**
     * Verify that an [CodigoRetencion] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validCodigoRetencionValue(value: String) {
        val result = CodigoRetencion.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that the CodigoRetencion MAX_LENGTH property returns the expected value
     */
    @Test
    fun codigoRetencionValueMaxLength() {
        kotlin.test.assertEquals(5, CodigoRetencion.MAX_LENGTH)
    }

    /**
     * Verify that [CodigoRetencion] equality uses value comparison and not reference comparison
     */
    @ParameterizedTest
    @MethodSource("getValidValues")
    fun alphanumericCodeValueEquality(value: String) {
        val result1 = CodigoRetencion.from(value)
        val result2 = CodigoRetencion.from(value)
        assertNotSame(result2, result1)
        assertEquals(result2, result1)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "1".repeat(6),  // value too long
            "1\nA",     // newline
            "1&A",      // invalid char
            "1-A",
            "1_A",
            "1+A",
            "1*A",
            "1.A",
            "1 A",      // whitespace
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "1",
            "11111",
            "1A",
            "1111A",
            "AAAAA"
        ).asArgs()
    }
}
