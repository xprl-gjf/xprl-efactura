package ec.com.xprl.efactura

internal const val TEXT_MAX_LENGTH = 300

/**
 * Immutable representation of a text field for a comprobante electrónico;
 * suitable for, e.g. [Emisor.razónSocial].
 */
open class TextValue protected constructor (value: CharSequence) {
    val value: String = value.toString()

    override fun toString() = value

    companion object {
        /**
         * Maximum length of a [TextValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = TEXT_MAX_LENGTH

        /**
         * Factory function to create a [TextValue] from a string.
         *
         * Note that HTML special characters (&,<,>) will be html-encoded,
         * e.g. as &amp; &lt; or &gt respectively.
         *
         * @exception IllegalArgumentException the string value is not a valid [TextValue].
         */
        @JvmStatic fun from(value: CharSequence) = TextValue(validate(htmlEncode(value)))

        @JvmStatic
        protected fun htmlEncode(value: CharSequence): CharSequence = value.replace(htmlEncodeRegex) {
            when (it.value) {
                "&" -> "&amp;"
                "<" -> "&lt;"
                ">" -> "&gt;"
                else -> it.value
            }
        }

        @JvmStatic
        protected fun validate(value: CharSequence, maxLength: Int = TEXT_MAX_LENGTH): CharSequence {
            if (value.length > maxLength) {
                throw IllegalArgumentException("'${value}' exceeds maximum length for a ${TextValue::class.java.simpleName}.")
            }
            if (value.contains(lineBreaksRegex)) {
                throw IllegalArgumentException("${TextValue::class.java.simpleName} value may not contain line breaks.")
            }
            return value
        }

        private val lineBreaksRegex = Regex("[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]")
        private val htmlEncodeRegex = Regex("[&<>]")
    }
}
