package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class NumericIdentifierTest {

    /**
     * Verify that an attempt to create an [NumericIdentifier] from
     * an invalid long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid NumericIdentifier from Long {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericLongValues")
    fun invalidNumericIdentifierLongValueException(value: Long) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            NumericIdentifier.from(value)
        }
    }

    /**
     * Verify that an attempt to create an [NumericIdentifier] from
     * an invalid unsigned long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid NumericIdentifier from ULong {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericULongValues")
    fun invalidNumericIdentifierULongValueException(value: Long) {
        // TODO: take test parameter as ULong. For now, Jupiter unit test API cannot handle kotlin ULong as Arguments
        val uLongValue = value.toULong()
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            NumericIdentifier.from(uLongValue)
        }
    }

    /**
     * Verify that an attempt to create an [NumericIdentifier] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid NumericIdentifier from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericStringValues")
    fun invalidNumericIdentifierStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            NumericIdentifier.from(value)
        }
    }

    /**
     * Verify that an [NumericIdentifier] can be created from a
     * valid value (String, ULong or Long).
     */
    @ParameterizedTest(name = "valid NumericIdentifier value {0}")
    @MethodSource("getValidNumericStringValues")
    fun validNumericIdentifierValue(value: String) {
        val result = NumericIdentifier.from(value)
        Assertions.assertEquals(value, result.value)

        val longResult = NumericIdentifier.from(value.toLong())
        Assertions.assertEquals(value, longResult.value)

        val uLongResult = NumericIdentifier.from(value.toULong())
        Assertions.assertEquals(value, uLongResult.value)
    }

    /**
     * Verify that the NumericIdentifier MAX_LENGTH property returns the expected value
     */
    @Test
    fun numericIdentifierMaxLength() {
        assertEquals(13, NumericIdentifier.MAX_LENGTH)
    }


    companion object {
        @JvmStatic
        private fun getInvalidNumericLongValues(): List<Arguments> = arrayOf(
            -1L,
            10000000000000L,
        ).asArgs()

        @JvmStatic
        private fun getInvalidNumericULongValues(): List<Arguments> = arrayOf(
            // TODO: take test parameter as ULong. For now, Jupiter unit test API cannot handle kotlin ULong as Arguments
            10000000000000L,
        ).asArgs()


        @JvmStatic
        private fun getInvalidNumericStringValues(): List<Arguments> = arrayOf(
            "",             // empty string
            " ",            // blank string
            "-1",           // negative
            "9".repeat(14),  // value too long
            "A",            // non-numeric
            "123-456",      // invalid char
            "123_123",      // invalid char
            "123&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidNumericStringValues(): List<Arguments> = arrayOf(
            "9".repeat(13),
            "1",
        ).asArgs()
    }
}