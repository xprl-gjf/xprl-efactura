package ec.com.xprl.efactura

private const val CODE_VALUE_MAX_LENGTH = 3

/**
 * Immutable representation of a short numeric code value;
 * suitable for, e.g. [Emisor.códigoEstablecimiento].
 */
class CodeValue private constructor (val value: Int) {

    override fun toString() = value.toString()

    companion object {
        /**
         * Maximum length of a [CodeValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = CODE_VALUE_MAX_LENGTH

        /**
         * Factory function to create an [CodeValue] from a string.
         * @exception IllegalArgumentException the string value is not a valid [CodeValue].
         */
        @JvmStatic fun from(value: CharSequence) = CodeValue(validate(value))
        /**
         * Factory function to create an [CodeValue] from an integer.
         * @exception IllegalArgumentException the integer value is not a valid [CodeValue].
         */
        @JvmStatic fun from(value: Int) = CodeValue(validate(value.toString()))
        /**
         * Factory function to create an [CodeValue] from an unsigned integer.
         * @exception IllegalArgumentException the integer value is not a valid [CodeValue].
         */
        @JvmStatic fun from(value: UInt) = CodeValue(validate(value.toString()))

        private fun validate(value: CharSequence) =
            validateCharSequence<CodeValue>(
                value,
                "numeric",
                CODE_VALUE_MAX_LENGTH,
                allowBlank=false
            ) {
                it.isValidNumeric()
            }.toString().toInt()
    }
}


