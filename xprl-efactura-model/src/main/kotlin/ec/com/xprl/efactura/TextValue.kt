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
         * Note that HTML special characters (&,<,>,/) will be html-encoded,
         * e.g. as &amp; &lt; &gt or &sol; respectively.
         *
         * @exception IllegalArgumentException the string value is not a valid [TextValue].
         */
        @JvmStatic fun from(value: CharSequence) = TextValue(validate(htmlEncode(value)))

        @JvmStatic
        // call htmlDecode() before encoding to prevent double-encoding of ampersand,
        // e.g. "&" -> "&amp;" -> "&amp;amp:" etc.
        protected fun htmlEncode(value: CharSequence): CharSequence = htmlDecode(value).replace(htmlEncodeRegex) {
            when (it.value) {
                "&" -> "&amp;"
                "<" -> "&lt;"
                ">" -> "&gt;"
                "/" -> "&sol;"
                else -> it.value
            }
        }

        @JvmStatic
        protected fun htmlDecode(value: CharSequence): CharSequence = value.replace(htmlDecodeRegex) {
            when (it.value) {
                "&amp;" -> "&"
                "&lt;" -> "<"
                "&gt;" -> ">"
                "&sol;" -> "/"
                else -> it.value
            }
        }

        @JvmStatic
        private fun validate(value: CharSequence) = validate(TextValue::class.java, value)

        @JvmStatic
        protected fun <T : TextValue> validate(
            clazz: Class<T>,
            value: CharSequence,
            maxLength: Int = TEXT_MAX_LENGTH,
            multiLine: Boolean = false
        ): CharSequence {
            if (value.length > maxLength) {
                throw IllegalArgumentException("'${value}' exceeds maximum length of ${maxLength} for a ${clazz.simpleName}.")
            }
            if (!multiLine && value.contains(lineBreaksRegex)) {
                throw IllegalArgumentException("${clazz.simpleName} value may not contain line breaks.")
            }
            return value
        }

        private val lineBreaksRegex = Regex("[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]")
        private val htmlEncodeRegex = Regex("[&<>/]")
        private val htmlDecodeRegex = Regex("(&amp;|&lt;|&gt;|&sol;)")
    }
}
