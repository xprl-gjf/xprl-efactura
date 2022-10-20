package ec.com.xprl.efactura

private const val ALPHANUMERIC_CODE_VALUE_MAX_LENGTH = 25

/**
 * Immutable representation of an alphanumeric code value;
 * suitable for, e.g. [ComprobanteDetalle.codigoPrincipal].
 */
class AlphanumericCodeValue private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AlphanumericCodeValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of an [AlphanumericCodeValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = ALPHANUMERIC_CODE_VALUE_MAX_LENGTH

        /**
         * Factory function to create an [AlphanumericCodeValue] from a string.
         * @exception IllegalArgumentException the string value is not a valid [AlphanumericCodeValue].
         */
        @JvmStatic fun from(value: CharSequence) = AlphanumericCodeValue(validate(value).toString())

        private fun validate(value: CharSequence) =
            validateCharSequence<AlphanumericCodeValue>(
                value,
                "alphanumeric id",
                ALPHANUMERIC_CODE_VALUE_MAX_LENGTH,
            ) {
                val allowedSymbols = "_-+*.".toList()
                it.isValidSimpleAlpha(allowedSymbols)
            }
    }
}