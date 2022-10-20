package ec.com.xprl.efactura

private const val CORREO_TEXT_MAX_LENGTH = 100

/**
 * Immutable representation of a short text field for a Correo;
 * suitable for, e.g. [Factura.TipoNegociable.correo].
 */
class CorreoValue private constructor (value: CharSequence)
    : TextValue(value) {

    companion object {
        /**
         * Maximum length of a [CorreoValue].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() {
                return CORREO_TEXT_MAX_LENGTH
            }

        /**
         * Factory function to create a [CorreoValue] from a string.
         *
         * Note that HTML special characters (&,<,>,/) will be html-encoded,
         * e.g. as &amp; &lt; &gt or &sol; respectively.
         *
         * @exception IllegalArgumentException the string value is not a valid [CorreoValue].
         */
        @JvmStatic fun from(value: CharSequence) = CorreoValue(validate(htmlEncode(value)))

        private fun validate(value: CharSequence): CharSequence {
            if (value.isBlank()) {
                    throw IllegalArgumentException("${CorreoValue::class.java.simpleName} cannot be blank.")
            }
            return validate(CorreoValue::class.java, value, CORREO_TEXT_MAX_LENGTH)
        }
    }
}