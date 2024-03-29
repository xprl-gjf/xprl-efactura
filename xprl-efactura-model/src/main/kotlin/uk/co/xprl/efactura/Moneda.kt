package uk.co.xprl.efactura

internal const val MONEDA_MAX_LENGTH = 15

/**
 * Immutable representation of a currency value for the 'moneda' field
 * in a comprobante electrónico.
 */
class Moneda private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Moneda
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [Moneda] value.
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = MONEDA_MAX_LENGTH

        /**
         * The default Moneda value.
         */
        val default
            @JvmStatic get() = DOLAR

        /**
         * The well-known currency value of "DOLAR".
         */
        val DOLAR
            @JvmStatic get() = _dolar
        private val _dolar = Moneda("DOLAR")

        /**
         * Factory function to create a [Moneda] value from an arbitrary string.
         * @exception IllegalArgumentException the string is not a valid [Moneda] value.
         */
        fun from(value: CharSequence) = Moneda(validate(value))

        private fun validate(value: CharSequence) = validateCharSequence<Moneda>(
            value,
            "simple word",
            MONEDA_MAX_LENGTH,
            allowBlank=false
        ) {
            it.isValidSimpleAlpha()
        }.toString()
    }
}
