package ec.com.xprl.efactura

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class RiseTest {

    /**
     * Verify that an attempt to create a [Rise] from
     * an invalid string value raises an [IllegalArgumentException].
     */
    @ParameterizedTest(name = "invalid value {0} throws IllegalArgumentException")
    @MethodSource("getInvalidValues")
    fun invalidRiseException(value: String) {
        assertThrows(IllegalArgumentException::class.java) {
            Rise.from(value)
        }
    }

    /**
     * Verify that a [Rise] can be created from a
     * valid string value.
     */
    @ParameterizedTest(name = "valid value {0}")
    @MethodSource("getValidValues")
    fun validRise(value: String) {
        val result = Rise.from(value)
        assertEquals(value, result.value)
    }

    /**
     * Verify that the Rise MAX_LENGTH property returns the expected value
     */
    @Test
    fun riseMaxLength() {
        kotlin.test.assertEquals(40, Rise.MAX_LENGTH)
    }

    companion object {
        @JvmStatic
        private fun getInvalidValues(): List<Arguments> = arrayOf(
            "",             // empty
            " ",            // blank
            "X".repeat(41),  // value too long
            "ABC\nXYZ",     // newline
            "ABC&123",      // invalid char
        ).asArgs()

        @JvmStatic
        private fun getValidValues(): List<Arguments> = arrayOf(
            "X".repeat(40),
            "ABC xyz",
        ).asArgs()
    }
}
