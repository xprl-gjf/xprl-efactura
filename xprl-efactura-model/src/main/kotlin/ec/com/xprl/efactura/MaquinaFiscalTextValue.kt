package ec.com.xprl.efactura

private const val MAQUINA_FISCAL_TEXT_MAX_LENGTH = 30

/**
 * Immutable representation of a short text field for a maquina fiscal;
 * suitable for, e.g. [MaquinaFiscal.marca].
 */
class MaquinaFiscalTextValue private constructor (value: CharSequence)
    : TextValue(value) {

    companion object {
        /**
         * Maximum length of a [MaquinaFiscalTextValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() {
                return MAQUINA_FISCAL_TEXT_MAX_LENGTH
            }

        /**
         * Factory function to create a [MaquinaFiscalTextValue] from a string.
         *
         * Note that HTML special characters (&,<,>,/) will be html-encoded,
         * e.g. as &amp; &lt; &gt or &sol; respectively.
         *
         * @exception IllegalArgumentException the string value is not a valid [MaquinaFiscalTextValue].
         */
        @JvmStatic fun from(value: CharSequence) = MaquinaFiscalTextValue(validate(htmlEncode(value)))

        private fun validate(value: CharSequence): CharSequence {
            if (value.isBlank()) {
                    throw IllegalArgumentException("${MaquinaFiscalTextValue::class.java.simpleName} cannot be blank.")
            }
            return validate(MaquinaFiscalTextValue::class.java, value, MAQUINA_FISCAL_TEXT_MAX_LENGTH)
        }
    }
}