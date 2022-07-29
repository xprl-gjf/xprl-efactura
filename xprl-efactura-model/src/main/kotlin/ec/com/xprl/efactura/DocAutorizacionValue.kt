package ec.com.xprl.efactura

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

    companion object {
        /**
         * Maximum length of a [DocAutorizacionValue] value.
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = DocAutorizacionValueLengths.maxOf { it }

        val VALID_LENGTHS: Array<Int>
            @JvmStatic get() = DocAutorizacionValueLengths.toTypedArray()

        /**
         * Factory function to create a [DocumentId] value from an arbitrary string.
         * @exception IllegalArgumentException the string is not a valid [DocumentId] value.
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