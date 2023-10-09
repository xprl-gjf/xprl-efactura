package uk.co.xprl.efactura

/**
 * Immutable representation of a multi-line text field for a comprobante electrónico;
 * suitable for, e.g. [InfoAdicional].
 */
class MultiLineTextValue private constructor (value: CharSequence)
    : TextValue(value) {

    companion object {
        /**
         * Maximum length of a [MultiLineTextValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = TEXT_MAX_LENGTH

        /**
         * Factory function to create a [MultiLineTextValue] from a string.
         *
         * Note that the value is validated with special characters (&,<,>) xml-encoded
         * (because this affects string length) but the stored value is _not_ xml-encoded.
         *
         * @exception IllegalArgumentException the string value is not a valid [MultiLineTextValue].
         */
        @JvmStatic fun from(value: CharSequence) = MultiLineTextValue(validate(value))

        private fun validate(value: CharSequence): CharSequence {
            return validate(MultiLineTextValue::class.java, value, multiLine = true)
        }
    }
}