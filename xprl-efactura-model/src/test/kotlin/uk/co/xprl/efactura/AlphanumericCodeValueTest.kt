package uk.co.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AlphanumericCodeValueTest {

    /**
     * Verify that an attempt to create an [AlphanumericCodeValue] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidAlphanumericCodeValueException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            AlphanumericCodeValue.from(value)
        }
    }

    /**
     * Verify that an [AlphanumericCodeValue] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validAlphanumericCodeValue(value: String) {
        val result = AlphanumericCodeValue.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that the AlphanumericCodeValue MAX_LENGTH property returns the expected value
     */
    @Test
    fun alphanumericCodeValueMaxLength() {
        kotlin.test.assertEquals(25, AlphanumericCodeValue.MAX_LENGTH)
    }

    /**
     * Verify that [AlphanumericCodeValue] equality uses value comparison and not reference comparison
     */
    @ParameterizedTest
    @MethodSource("getValidValues")
    fun alphanumericCodeValueEquality(value: String) {
        val result1 = AlphanumericCodeValue.from(value)
        val result2 = AlphanumericCodeValue.from(value)
        assertNotSame(result2, result1)
        assertEquals(result2, result1)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "X".repeat(26),  // value too long
            "ABC\n123",     // newline
            "ABC&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "X".repeat(25),
            "ABC123",
            "ABC-123",
            "ABC_123",
            "ABC+123",
            "ABC*123",
            "ABC.123",
            "ABC 123",      // whitespace
        ).asArgs()
    }
}
