package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CorreoValueTest {

    /**
     * Verify that an attempt to create an [CorreoValue] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidTextValueException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            CorreoValue.from(value)
        }
    }

    /**
     * Verify that an [CorreoValue] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validTextValue(value: String) {
        val result = CorreoValue.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that when a [CorreoValue] is created from a string containing
     * 'special characters' (e.g. &<>/), then those characters are substituted
     * with corresponding HTML entity references (e.g. &amp; &lt; &gt; &sol;).
     */
    @ParameterizedTest(name = "html-escaped value {0}")
    @MethodSource("getHtmlEscapedValues")
    fun htmlEncodedTextValue(value: String, expected: String) {
        val result = CorreoValue.from(value)
        assertEquals(expected, result.value)
    }

    /**
     * Verify that the CorreoValue MAX_LENGTH property returns the expected value
     */
    @Test
    fun textValueMaxLength() {
        kotlin.test.assertEquals(100, CorreoValue.MAX_LENGTH)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "X".repeat(101),  // value too long
            "&" + "X".repeat(98),  // html-escape causes value to be too long
            "ABC\n123",     // newline
            "ABC\u000A123", // line feed
            "ABC\u000B123", // vertical tab
            "ABC\u000C123", // form feed
            "ABC\u000D123", // carriage return
            "ABC\u0085123", // next-line
            "ABC\u2028123", // line separator
            "ABC\u2029123", // paragraph separator
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "X".repeat(100),
            "ABC123",
            "ABC-123",
            "ABC_123"
        ).asArgs()

        @JvmStatic
        private fun getHtmlEscapedValues(): List<Arguments> = listOf(
            arguments("ABC&123", "ABC&amp;123"),
            arguments("A&<>/1", "A&amp;&lt;&gt;&sol;1"),
            arguments(">", "&gt;"),      // solitary html-escaped char
            arguments("&".repeat(4), "&amp;".repeat(4))
        )
    }
}
