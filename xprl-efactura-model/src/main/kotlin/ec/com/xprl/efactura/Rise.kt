package ec.com.xprl.efactura

internal const val RISE_MAX_LENGTH = 40

/**
 * Immutable representation of a 'rise' value,
 * e.g. for [NotaCredito.Credito.rise]
 */
class Rise private constructor (val value: String) {

    override fun toString() = value.toString()

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Rise
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [Rise] value.
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = RISE_MAX_LENGTH

        /**
         * Factory function to create a [Rise] value from an arbitrary string.
         * @exception IllegalArgumentException the string is not a valid [Moneda] value.
         */
        fun from(value: CharSequence) = Rise(validate(value))

        private fun validate(value: CharSequence) = validateCharSequence<Rise>(
            value,
            "alphabetic",
            RISE_MAX_LENGTH,
            allowBlank=false
        ) {
            it.isValidSimpleAlpha()
        }.toString()
    }
}

