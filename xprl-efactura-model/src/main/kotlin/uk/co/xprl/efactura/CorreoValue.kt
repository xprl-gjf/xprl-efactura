package uk.co.xprl.efactura

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
         * Note that the value is validated with special characters (&,<,>) xml-encoded
         * (because this affects string length) but the stored value is _not_ xml-encoded.
         *
         * @exception IllegalArgumentException the string value is not a valid [CorreoValue].
         */
        @JvmStatic fun from(value: CharSequence) = CorreoValue(validate(value))

        private fun validate(value: CharSequence): CharSequence {
            if (value.isBlank()) {
                    throw IllegalArgumentException("${CorreoValue::class.java.simpleName} cannot be blank.")
            }
            return validate(CorreoValue::class.java, value, CORREO_TEXT_MAX_LENGTH)
        }
    }
}