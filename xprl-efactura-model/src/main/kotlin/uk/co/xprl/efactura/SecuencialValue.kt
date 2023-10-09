package uk.co.xprl.efactura

private const val SECUENCIAL_VALUE_MAX_LENGTH = 9

/**
 * Un valor por el campo `secuencial` de un comprobante electrónico.
 */
class SecuencialValue private constructor (val value: Int) {

    override fun toString() = value.toString()

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SecuencialValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [SecuencialValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = SECUENCIAL_VALUE_MAX_LENGTH

        /**
         * Factory function to create a [SecuencialValue] from a string.
         * @exception IllegalArgumentException the string value is not a valid [SecuencialValue].
         */
        @JvmStatic fun from(value: CharSequence) = SecuencialValue(validate(value))
        /**
         * Factory function to create a [SecuencialValue] from an integer.
         * @exception IllegalArgumentException the value is not a valid [SecuencialValue].
         */
        @JvmStatic fun from(value: Int) = SecuencialValue(validate(value.toString()))
        /**
         * Factory function to create a [SecuencialValue] from an unsigned integer.
         * @exception IllegalArgumentException the value is not a valid [SecuencialValue].
         */
        @JvmStatic fun from(value: UInt) = SecuencialValue(validate(value.toString()))

        private fun validate(value: CharSequence) = validateCharSequence<SecuencialValue>(
                value,
                "numeric",
                SECUENCIAL_VALUE_MAX_LENGTH,
                allowBlank=false
            ) {
                it.isValidNumeric()
            }.toString().toInt()
    }
}