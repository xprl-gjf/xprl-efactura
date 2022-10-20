package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class CodeValueTest {

    /**
     * Verify that an attempt to create a [CodeValue] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid String value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidStringValues")
    fun invalidCodeStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            CodeValue.from(value)
        }
    }

    /**
     * Verify that an attempt to create a [CodeValue] from
     * an invalid integer value raises an [IllegalArgumentException].
     */
   @ParameterizedTest(name = "invalid Int value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidIntValues")
    fun invalidCodeIntValueException(value: Int) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            CodeValue.from(value)
        }
    }

    /**
     * Verify that an attempt to create a [CodeValue] from
     * an invalid unsigned integer value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid UInt value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidUIntValues")
    fun invalidCodeUIntValueException(value: UInt) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            CodeValue.from(value)
        }
    }

    /**
     * Verify that a [CodeValue] can be created from a
     * valid value (Int, UInt or String).
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validIntCodeValue(value: Int) {
        val intResult = CodeValue.from(value)
        Assertions.assertEquals(value, intResult.value)

        val uintValue: UInt = value.toUInt()
        val uintResult = CodeValue.from(uintValue)
        Assertions.assertEquals(value, uintResult.value)

        val strValue: String = value.toString()
        val strResult = CodeValue.from(strValue)
        Assertions.assertEquals(value, strResult.value)
    }

    /**
     * Verify that the CodeValue MAX_LENGTH property returns the expected value
     */
    @Test
    fun codeValueMaxLength() {
        assertEquals(3, CodeValue.MAX_LENGTH)
    }

    /**
     * Verify that [CodeValue] equality uses value comparison and not reference comparison
     */
    @ParameterizedTest
    @MethodSource("getValidValues")
    fun codeValueEquality(value: Int) {
        val result1 = CodeValue.from(value)
        val result2 = CodeValue.from(value)
        Assertions.assertNotSame(result2, result1)
        Assertions.assertEquals(result2, result1)
    }

    companion object {
        @JvmStatic
        private fun getInvalidIntValues(): List<Arguments> = arrayOf(
            1000,  // value too long
            -1,  // negative value
        ).asArgs()

        @JvmStatic
        private fun getInvalidUIntValues(): List<Arguments> = arrayOf(
            1000,  // value too long
        ).asArgs()

        @JvmStatic
        private fun getInvalidStringValues(): List<Arguments> = getInvalidIntValues()
            .map {
                Arguments.of(it.get()[0].toString())
            } + arrayOf(
                "",
                " ",
                "abc",
                "&1"
            ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            0,
            1,
            99,
            999
        ).asArgs()
    }
}