package ec.com.xprl.efactura

private const val SHORT_TEXT_MAX_LENGTH = 20

/**
 * Immutable representation of a short text field for a comprobante electrónico;
 * suitable for, e.g. [GuiaRemision.Destinatario.identificación].
 */
class ShortTextValue private constructor (value: CharSequence)
    : TextValue(value) {

    companion object {
        /**
         * Maximum length of a [ShortTextValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = SHORT_TEXT_MAX_LENGTH

        /**
         * Factory function to create a [ShortTextValue] from a string.
         *
         * Note that the value is validated with special characters (&,<,>) xml-encoded
         * (because this affects string length) but the stored value is _not_ xml-encoded.
         *
         * @exception IllegalArgumentException the string value is not a valid [ShortTextValue].
         */
        @JvmStatic fun from(value: CharSequence) = ShortTextValue(validate(value))

        private fun validate(value: CharSequence): CharSequence {
            if (value.isBlank()) {
                    throw IllegalArgumentException("${ShortTextValue::class.java.simpleName} cannot be blank.")
            }
            return validate(ShortTextValue::class.java, value, SHORT_TEXT_MAX_LENGTH)
        }
    }
}