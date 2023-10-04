package uk.co.xprl.efactura

private const val PAIS_CODE_VALUE_MAX_LENGTH = 4

/**
 * Immutable representation of a short numeric code value;
 * suitable for, e.g. [PagoRetencionATS.pais].
 */
class PaisCodeValue private constructor (val value: Int) {

    override fun toString() = value.toString()

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PaisCodeValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [PaisCodeValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = PAIS_CODE_VALUE_MAX_LENGTH

        /**
         * Factory function to create an [PaisCodeValue] from a string.
         * @exception IllegalArgumentException the string value is not a valid [PaisCodeValue].
         */
        @JvmStatic fun from(value: CharSequence) = PaisCodeValue(validate(value))
        /**
         * Factory function to create an [PaisCodeValue] from an integer.
         * @exception IllegalArgumentException the integer value is not a valid [PaisCodeValue].
         */
        @JvmStatic fun from(value: Int) = PaisCodeValue(validate(value.toString()))
        /**
         * Factory function to create an [PaisCodeValue] from an unsigned integer.
         * @exception IllegalArgumentException the integer value is not a valid [PaisCodeValue].
         */
        @JvmStatic fun from(value: UInt) = PaisCodeValue(validate(value.toString()))

        private fun validate(value: CharSequence) =
            validateCharSequence<PaisCodeValue>(
                value,
                "numeric pais code",
                PAIS_CODE_VALUE_MAX_LENGTH,
                allowBlank = false
            ) {
                it.isValidNumeric()
            }.toString().toInt()
    }
}