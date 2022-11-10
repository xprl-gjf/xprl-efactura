package ec.com.xprl.efactura

import ec.com.xprl.efactura.IdentityValue.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class IdentityValueTest {

    /**
     * Verify that [IdentityValue] equality uses value comparison and not reference comparison
     */
    @Suppress("AssertBetweenInconvertibleTypes")
    @ParameterizedTest(name = "RUC value {0}")
    @MethodSource("getValidRUCStringValues")
    fun identityValueEquality(value: String) {
        val result1 = RUC.from(value)
        val result2 = RUC.from(value)

        val notEquals = ConsumidorFinal

        Assertions.assertNotSame(result2, result1)
        Assertions.assertEquals(result2, result1)

        Assertions.assertNotEquals(notEquals, result1)
    }

    /**
     * Verify that an attempt to create a [RUC] identity value from
     * an invalid long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid RUC from Long {0} throws IllegalArgumentException")
    @MethodSource("getInvalidRUCLongValues")
    fun invalidRUCLongValueException(value: Long) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            RUC.from(value)
        }
    }

    /**
     * Verify that an attempt to create a [RUC] identity value from
     * an invalid unsigned long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid RUC from ULong {0} throws IllegalArgumentException")
    @MethodSource("getInvalidRUCULongValues")
    fun invalidRUCULongValueException(value: Long) {
        // TODO: take test parameter as ULong. For now, Jupiter unit test API cannot handle kotlin ULong as Arguments
        val uLongValue = value.toULong()
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            RUC.from(uLongValue)
        }
    }

    /**
     * Verify that an attempt to create a [RUC] identity value from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid RUC from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidRUCStringValues")
    fun invalidRUCStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            RUC.from(value)
        }
    }

    /**
     * Verify that a [RUC] identity value can be created from a
     * valid value (string, long or ulong).
     */
    @ParameterizedTest(name = "valid RUC value {0}")
    @MethodSource("getValidRUCStringValues")
    fun validRUCValue(value: String) {
        val result = RUC.from(value)
        Assertions.assertEquals(value, result.value)

        val longResult = RUC.from(value.toLong())
        Assertions.assertEquals(value, longResult.value)

        val uLongResult = RUC.from(value.toULong())
        Assertions.assertEquals(value, uLongResult.value)
    }

    /**
     * Verify that an attempt to create a [Cedula] identity value from
     * an invalid long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid Cedula from Long {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericLongValues")
    fun invalidCedulaLongValueException(value: Long) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Cedula.from(value)
        }
    }

    /**
     * Verify that an attempt to create a [Cedula] identity value from
     * an invalid unsigned long integer raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid Cedula from ULong {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericULongValues")
    fun invalidCedulaULongValueException(value: Long) {
        // TODO: take test parameter as ULong. For now, Jupiter unit test API cannot handle kotlin ULong as Arguments
        val uLongValue = value.toULong()
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Cedula.from(uLongValue)
        }
    }

    /**
     * Verify that an attempt to create a [Cedula] identity value from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid Cedula from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidNumericStringValues")
    fun invalidCedulaStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Cedula.from(value)
        }
    }

    /**
     * Verify that a [Cedula] identity value can be created from a
     * valid value (string, long or ulong).
     */
    @ParameterizedTest(name = "valid Cedula value {0}")
    @MethodSource("getValidNumericStringValues")
    fun validCedulaValue(value: String) {
        val result = Cedula.from(value)
        Assertions.assertEquals(value, result.value)

        val longResult = Cedula.from(value.toLong())
        Assertions.assertEquals(value, longResult.value)

        val uLongResult = Cedula.from(value.toULong())
        Assertions.assertEquals(value, uLongResult.value)
    }


    /**
     * Verify that an attempt to create a [Pasaporte] identity value from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid Pasaporte from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidPasaporteStringValues")
    fun invalidPasaporteValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Pasaporte.from(value)
        }
    }

    /**
     * Verify that a [Pasaporte] identity value can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid Pasaporte from {0}")
    @MethodSource("getValidPasaporteStringValues")
    fun validPasaporteValue(value: String) {
        val result = Pasaporte.from(value)
        assertEquals(value, result.value)
        assertEquals(value, result.toString())
    }


    /**
     * Verify that an attempt to create an [IdentificacionDelExterior] identity value from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid IdentificacionDelExterior from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidIdentifierStringValues")
    fun invalidExteriorValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            IdentificacionDelExterior.from(value)
        }
    }

    /**
     * Verify that a [IdentificacionDelExterior] identity value can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid IdentificacionDelExterior from {0}")
    @MethodSource("getValidIdentifierStringValues")
    fun validExteriorValue(value: String) {
        val result = IdentificacionDelExterior.from(value)
        assertEquals(value, result.value)
        assertEquals(value, result.toString())
    }

    /**
     * Verify that a [ConsumidorFinal] identity value can be created, and that
     * it has a value of "9999999999999".
     */
    @Test
    fun consumidorFinalValue() {
        val expected = "9999999999999"
        val id = ConsumidorFinal
        assertEquals(expected, id.value)
        assertEquals(expected, id.toString())
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
            "",             // empty
            " ",            // whitespace
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

        @JvmStatic
        private fun getInvalidRUCLongValues(): List<Arguments> =
            getInvalidNumericLongValues() + arrayOf(
                111111111111L,      // RUC must be 13 digits
                1111111111L,        // RUC must be 13 digits
                1L,                 // RUC must be 13 digits
            ).asArgs()

        @JvmStatic
        private fun getInvalidRUCULongValues(): List<Arguments> =
            getInvalidRUCLongValues()

        @JvmStatic
        private fun getInvalidRUCStringValues(): List<Arguments> =
            getInvalidNumericStringValues() + arrayOf(
                "111111111111",      // RUC must be 13 digits
                "1111111111",        // RUC must be 13 digits
                "1",                 // RUC must be 13 digits
            ).asArgs()

        @JvmStatic
        private fun getValidRUCStringValues(): List<Arguments> = arrayOf(
            "1111111111111",
            "9999999999999"
        ).asArgs()

        @JvmStatic
        private fun getInvalidIdentifierStringValues(): List<Arguments> = arrayOf(
            " ",            // whitespace
            "-1",           // negative
            "9".repeat(14),  // value too long
            "ABC 456",      // invalid char
            "ABC-456",      // invalid char
            "ABC_123",      // invalid char
            "ABC&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidIdentifierStringValues(): List<Arguments> = arrayOf(
            "A",
            "ABC123",
            "9".repeat(13),  // value max length
        ).asArgs()

        @JvmStatic
        private fun getInvalidPasaporteStringValues(): List<Arguments> = arrayOf(
            " ",            // whitespace
            "-1",           // negative
            "9".repeat(15),  // value too long
            "ABC 456",      // invalid char
            "ABC-456",      // invalid char
            "ABC_123",      // invalid char
            "ABC&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidPasaporteStringValues(): List<Arguments> = arrayOf(
            "A",
            "ABC123",
            "9".repeat(14),  // value max length
        ).asArgs()
    }
}

internal fun <T> Array<T>.asArgs(): List<Arguments> = this.map { Arguments.of(it) }