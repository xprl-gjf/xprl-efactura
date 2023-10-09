package uk.co.xprl.efactura.jaxb

/**
 * Generate modulo11 checksum value for a 48-digit clave acceso.
 *
 * @return a single-digit integer checksum value (in the range [0-9]).
 * @throws IllegalArgumentException the given char sequence is not a string of 48-digits.
 */
internal fun modulo11(claveAcceso48: CharSequence): Int {
    val claveValor = try {
        ClaveAccesoSeed48.from(claveAcceso48)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Cannot calculate modulo11 checksum for '$claveAcceso48'", e)
    }

    val factores = cycle(2, 3, 4, 5, 6, 7)
    val suma = claveValor.values
        .reversed()
        .asSequence()
        .zip(factores)
        .fold(0) { accum, (digito, factor) ->
        accum + digito * factor
    }
    return when(val control = 11 - (suma % 11)) {
        11 -> 0
        10 -> 1
        else -> control
    }
}

private fun <T : Any> cycle(vararg xs: T): Sequence<T> {
    var i = 0
    return generateSequence { xs[i++ % xs.size] }
}

/**
 * Immutable value representing a sequence of 48 digits.
 */
private class ClaveAccesoSeed48 private constructor (val value: CharSequence) {

    /**
     * The sequence of digits as an array of integers.
     */
    val values: IntArray
        get() = value.map { it.digitToInt() }.toIntArray()

    override fun toString() = value.toString()

    companion object {
        /**
         * Factory function to create a [ClaveAccesoSeed48] from a string.
         * @exception IllegalArgumentException the string value is not a valid [ClaveAccesoSeed48].
         */
        fun from(value: CharSequence) = ClaveAccesoSeed48(validate(value))

        private fun validate(value: CharSequence) =
            validateNumeric<ClaveAccesoSeed48>(value, 48, 48, allowBlank = false)
    }
}

/**
 * Validate that a string value comprises a sequence of digits of a specific length.
 *
 * @throws IllegalArgumentException the string value does not represent a valid sequence of digits.
 */
internal inline fun <reified T: Any> validateNumeric(value: CharSequence, minDigits: Int, maxDigits: Int, allowBlank: Boolean = false): CharSequence {
    if (!allowBlank && value.isBlank()) {
        throw IllegalArgumentException("'${value}' is not a valid ${T::class.java.simpleName}. Value cannot be blank.")
    }
    if (value.length < minDigits) {
        throw IllegalArgumentException("'${value}' is less than minimum length for a ${T::class.java.simpleName}.")
    }
    if (value.length > maxDigits) {
        throw IllegalArgumentException("'${value}' exceeds maximum length for a ${T::class.java.simpleName}.")
    }
    if (value.any { !it.isDigit() }) {
        throw IllegalArgumentException("'${value}' is not a valid ${T::class.java.simpleName}. Value must be numeric.")
    }
    return value
}
