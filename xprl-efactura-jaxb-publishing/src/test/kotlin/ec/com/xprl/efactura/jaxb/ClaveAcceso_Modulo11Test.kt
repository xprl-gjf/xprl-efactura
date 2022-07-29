package ec.com.xprl.efactura.jaxb

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class ClaveAcceso_Modulo11Test {

    @ParameterizedTest(name = "modulo11 calculates checksum for {0}")
    @MethodSource("getValues")
    fun modulo11CheckSumValue(claveAcceso48: String, expected: Int) {
        assertEquals(expected, modulo11(claveAcceso48))
    }

    @ParameterizedTest(name = "modulo11 throws exception for {0}")
    @MethodSource("getInvalidValues")
    fun modulo11IllegalArgumentException(invalid: String) {
        assertThrows<IllegalArgumentException> {
            modulo11(invalid)
        }
    }

    companion object {
        @JvmStatic
        fun getValues(): List<Arguments> = sequence {
            yield(arguments("250520220117927582700012002004000006794012877011", 9))
            yield(arguments("021220140117917369580011001100000000237000000861", 0))
            yield(arguments("111220140117917369580011001100000000286000001271", 1))
            yield(arguments("161220140117917369580011001001000000030000001671", 9))
            yield(arguments("031220140117917369580011001100000000245000000941", 0))
        }.toList()

        @JvmStatic
        fun getInvalidValues(): List<Arguments> = arrayOf(
            "2505202201179275827000120020040000067940128770119", // too long
            "25052022011792758270001200200400000679401287701",   // too short
            "25052022011792758270001200200400000679401287701A",  // non-numeric
            "-25052022011792758270001200200400000679401287701",  // -ve
            "",                                                  // empty
            " ",                                                 // blank
        ).map {
            arguments(it)
        }
    }
}