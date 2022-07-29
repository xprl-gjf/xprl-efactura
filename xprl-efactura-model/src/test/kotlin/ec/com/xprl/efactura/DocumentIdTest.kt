package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class DocumentIdTest {

    /**
     * Verify that an attempt to create a [DocumentId] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidDocumentIdException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            DocumentId.from(value)
        }
    }

    /**
     * Verify that a [DocumentId] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validDocumentId(value: String) {
        val result = DocumentId.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that the DocumentId MAX_LENGTH property returns the expected value
     */
    @Test
    fun documentIdMaxLength() {
        kotlin.test.assertEquals(17, DocumentId.MAX_LENGTH)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "111-111-9999999999",  // value too long
            "123\n456",     // newline
            "123&456",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "1",
            "111-111-999999999",
            "123_456",
            "123-456"
        ).asArgs()
    }
}
