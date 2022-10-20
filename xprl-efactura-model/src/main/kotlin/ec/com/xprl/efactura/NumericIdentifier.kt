package ec.com.xprl.efactura

internal const val IDENTIFIER_MAX_LENGTH = 13

/**
 * Immutable representation of a numeric identifier value;
 * suitable for, e.g. [Emisor.contribuyenteEspecial].
 */
class NumericIdentifier private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NumericIdentifier
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [NumericIdentifier].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = IDENTIFIER_MAX_LENGTH

        /**
         * Factory function to create a [NumericIdentifier] from a string.
         * @exception IllegalArgumentException the string value is not a valid [NumericIdentifier].
         */
        fun from(value: CharSequence) = NumericIdentifier(validate(value))
        /**
         * Factory function to create a [NumericIdentifier] from a long integer.
         * @exception IllegalArgumentException the value is not a valid [NumericIdentifier].
         */
        fun from(value: Long) = NumericIdentifier(validate(value.toString()))
        /**
         * Factory function to create a [NumericIdentifier] from an unsigned long integer.
         * @exception IllegalArgumentException the value is not a valid [NumericIdentifier].
         */
        fun from(value: ULong) = NumericIdentifier(validate(value.toString()))

        private fun validate(value: CharSequence) =
            validateCharSequence<NumericIdentifier>(
                value,
                "numeric",
                IDENTIFIER_MAX_LENGTH,
                allowBlank=false
            ) {
                it.isValidNumeric()
            }.toString()
    }
}
