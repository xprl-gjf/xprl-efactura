package ec.com.xprl.efactura.test

import ec.com.xprl.efactura.DocAutorizacionValue
import ec.com.xprl.efactura.asArgs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class DocAutorizacionValueTest {

    /**
     * Verify that an attempt to create an [DocAutorizacionValue] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid DocAutorizacionValue from String {0} throws IllegalArgumentException")
    @MethodSource("getInvalidStringValues")
    fun invalidDocAutorizacionValueStringValueException(value: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            DocAutorizacionValue.from(value)
        }
    }

    /**
     * Verify that an [DocAutorizacionValue] can be created from a
     * valid String value.
     */
    @ParameterizedTest(name = "valid DocAutorizacionValue value {0}")
    @MethodSource("getValidStringValues")
    fun validDocAutorizacionValueValue(value: String) {
        val result = DocAutorizacionValue.from(value)
        Assertions.assertEquals(value, result.value)
    }

    /**
     * Verify that the DocAutorizacionValue MAX_LENGTH property returns the expected value
     */
    @Test
    fun docAutorizacionMaxLength() {
        assertEquals(49, DocAutorizacionValue.MAX_LENGTH)
    }

    /**
     * Verify that the DocAutorizacionValue VALID_LENGTHS property returns the expected value
     */
    @Test
    fun docAutorizacionValidLengths() {
        assertContains(DocAutorizacionValue.VALID_LENGTHS, 10)
        assertContains(DocAutorizacionValue.VALID_LENGTHS, 37)
        assertContains(DocAutorizacionValue.VALID_LENGTHS, 49)
    }

    companion object {
        @JvmStatic
        private fun getInvalidStringValues(): List<Arguments> = arrayOf(
            "",             // empty string
            " ",            // blank string
            "-1",           // negative
            "9".repeat(50),  // value too long
            "9",            // invalid length
            "9".repeat(38), // invalid length
            "A",            // non-numeric
            "123-456",      // invalid char
            "123_123",      // invalid char
            "123&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidStringValues(): List<Arguments> = arrayOf(
            "9".repeat(10),
            "1".repeat(37),
            "2".repeat(49)
        ).asArgs()
    }
}