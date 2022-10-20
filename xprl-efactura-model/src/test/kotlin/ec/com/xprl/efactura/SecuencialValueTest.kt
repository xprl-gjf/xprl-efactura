package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import kotlin.test.assertEquals

internal class SecuencialValueTest {

    /**
     * Verify that an attempt to create an [SecuencialValue] from
     * an invalid integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid SecuencialValue from Int {0} throws IllegalArgumentException")
    @MethodSource("getInvalidSecuencialIntValues")
    fun invalidSecuencialIntValueException(value: Int) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SecuencialValue.from(value)
        }
    }

    /**
     * Verify that an attempt to create an [SecuencialValue] from
     * an invalid unsigned integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid SecuencialValue from UInt {0} throws IllegalArgumentException")
    @MethodSource("getInvalidSecuencialUIntValues")
    fun invalidSecuencialUIntValueException(value: Int) {
        // TODO: take test parameter as ULong. For now, Jupiter unit test API cannot handle kotlin UInt as Arguments
        val uIntValue = value.toUInt()
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SecuencialValue.from(uIntValue)
        }
    }

    /**
     * Verify that an attempt to create an [SecuencialValue] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid SecuencialValue from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidSecuencialStringValues")
    fun invalidSecuencialStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SecuencialValue.from(value)
        }
    }

    /**
     * Verify that an [SecuencialValue] can be created from a
     * valid value (String, ULong or Long).
     */
    @ParameterizedTest(name = "valid SecuencialValue value {0}")
    @MethodSource("getValidSecuencialStringValues")
    fun validSequencialValue(value: String) {
        val intValue = value.toInt()

        val result = SecuencialValue.from(value)
        Assertions.assertEquals(intValue, result.value)

        val intResult = SecuencialValue.from(value.toInt())
        Assertions.assertEquals(intValue, intResult.value)

        val uIntResult = SecuencialValue.from(value.toUInt())
        Assertions.assertEquals(intValue, uIntResult.value)
    }

    /**
     * Verify that the SecuencialValue MAX_LENGTH property returns the expected value
     */
    @Test
    fun secuencialValueMaxLength() {
        assertEquals(9, SecuencialValue.MAX_LENGTH)
    }

    /**
     * Verify that [SecuencialValue] equality uses value comparison and not reference comparison
     */
    @ParameterizedTest
    @MethodSource("getValidSecuencialStringValues")
    fun secuencialValueEquality(value: String) {
        val result1 = SecuencialValue.from(value)
        val result2 = SecuencialValue.from(value)
        Assertions.assertNotSame(result2, result1)
        Assertions.assertEquals(result2, result1)
    }

    companion object {
        @JvmStatic
        private fun getInvalidSecuencialIntValues(): List<Arguments> = arrayOf(
            -1,
            1000000000,
        ).asArgs()

        @JvmStatic
        private fun getInvalidSecuencialUIntValues(): List<Arguments> = arrayOf(
            // TODO: take test parameter as UInt. For now, Jupiter unit test API cannot handle kotlin UInt as Arguments
            1000000000,
        ).asArgs()


        @JvmStatic
        private fun getInvalidSecuencialStringValues(): List<Arguments> = arrayOf(
            "",             // empty string
            " ",            // blank string
            "-1",           // negative
            "9".repeat(10),  // value too long
            "A",            // non-numeric
            "123-456",      // invalid char
            "123_123",      // invalid char
            "123&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidSecuencialStringValues(): List<Arguments> = arrayOf(
            "9".repeat(9),
            "1",
        ).asArgs()
    }
}