package uk.co.xprl.efactura

/**
 * Valid lengths for a DocAutorizacionValue
 */
internal val DocAutorizacionValueLengths: IntArray = intArrayOf(10, 37, 49)

/**
 * Immutable representation of a 'documento autorizacion' value,
 * e.g. for [GuiaRemision.Destinatario.numAutDocSustento]
 */
class DocAutorizacionValue private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DocAutorizacionValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [DocAutorizacionValue] value.
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = DocAutorizacionValueLengths.maxOf { it }

        val VALID_LENGTHS: Array<Int>
            @JvmStatic get() = DocAutorizacionValueLengths.toTypedArray()

        /**
         * Factory function to create a [DocAutorizacionValue] value from an arbitrary string.
         * @exception IllegalArgumentException the string is not a valid [DocAutorizacionValue] value.
         */
        fun from(value: CharSequence) = DocAutorizacionValue(validate(value))

        private fun validate(value: CharSequence): String {
            validateCharSequence<DocAutorizacionValue>(
                value,
                "numeric",
                MAX_LENGTH,
                allowBlank = false
            ) {
                it.isValidNumeric()
            }

            validateLength<DocAutorizacionValue>(value, *DocAutorizacionValueLengths)
            return value.toString()
        }
    }
}