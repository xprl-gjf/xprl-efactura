package ec.com.xprl.efactura

internal const val TEXT_MAX_LENGTH = 300

/**
 * Immutable representation of a text field for a comprobante electrónico;
 * suitable for, e.g. [Emisor.razónSocial].
 */
open class TextValue protected constructor (value: CharSequence) {
    val value: String = value.toString()

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TextValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [TextValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = TEXT_MAX_LENGTH

        /**
         * Factory function to create a [TextValue] from a string.
         *
         * @exception IllegalArgumentException the string value is not a valid [TextValue].
         */
        @JvmStatic fun from(value: CharSequence) = TextValue(validate(value))

        @JvmStatic
        private fun validate(value: CharSequence) = validate(TextValue::class.java, value)

        @JvmStatic
        /**
         * Validate a text value.
         *
         * Note that, for purposes of validation special characters (&,<,>)
         * will be xml-encoded (&amp; &lt; or &gt), because this affects
         * the string length.
         *
         * However, the returned validated value is _not_ xml-encoded.
         * The XML object marshaller or stream writer should perform
         * xml-encoding automatically.
         *
         * @return the successfully validated text value.
         * @throws IllegalArgumentException if the text value is not valid.
         */
        protected fun <T : TextValue> validate(
            clazz: Class<T>,
            value: CharSequence,
            maxLength: Int = TEXT_MAX_LENGTH,
            multiLine: Boolean = false
        ): CharSequence {
            val encoded = xmlEncode(value)
            if (encoded.length > maxLength) {
                throw IllegalArgumentException("'${encoded}' exceeds maximum length of ${maxLength} for a ${clazz.simpleName}.")
            }
            if (!multiLine && value.contains(lineBreaksRegex)) {
                throw IllegalArgumentException("${clazz.simpleName} value may not contain line breaks.")
            }
            return xmlDecode(value)
        }


        @JvmStatic
        // call xmlDecode() before encoding to prevent double-encoding of ampersand,
        // e.g. "&" -> "&amp;" -> "&amp;amp:" etc.
        private fun xmlEncode(value: CharSequence): CharSequence = xmlDecode(value).replace(xmlEncodeRegex) {
            when (it.value) {
                "&" -> "&amp;"
                "<" -> "&lt;"
                ">" -> "&gt;"
                else -> it.value
            }
        }

        @JvmStatic
        private fun xmlDecode(value: CharSequence): CharSequence = value.replace(xmlDecodeRegex) {
            when (it.value) {
                "&amp;" -> "&"
                "&lt;" -> "<"
                "&gt;" -> ">"
                else -> it.value
            }
        }

        private val lineBreaksRegex = Regex("[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]")
        private val xmlEncodeRegex = Regex("[&<>]")
        private val xmlDecodeRegex = Regex("(&amp;|&lt;|&gt;)")
    }
}
