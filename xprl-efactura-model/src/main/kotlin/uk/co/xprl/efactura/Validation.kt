@file:Suppress("unused")

package uk.co.xprl.efactura

inline fun <reified T: Any> validateCharSequence(
    value: CharSequence,
    descriptor: String,
    maxLength: Int,
    isValid: (Char) -> Boolean
) = validateCharSequence<T>(
    value, descriptor, minLength=0, maxLength, allowBlank=false, isValid
)

inline fun <reified T: Any> validateCharSequence(
    value: CharSequence,
    descriptor: String,
    maxLength: Int,
    allowBlank: Boolean,
    isValid: (Char) -> Boolean
) = validateCharSequence<T>(
    value, descriptor, minLength=0, maxLength, allowBlank, isValid
)

inline fun <reified T: Any> validateCharSequence(
    value: CharSequence,
    descriptor: String,
    minLength: Int,
    maxLength: Int,
    isValid: (Char) -> Boolean
) = validateCharSequence<T>(
    value, descriptor, minLength, maxLength, allowBlank=false, isValid
)

inline fun <reified T: Any> validateCharSequence(
    value: CharSequence,
    descriptor: String,
    minLength: Int,
    maxLength: Int,
    allowBlank: Boolean,
    isValid: (Char) -> Boolean,
): CharSequence {
    if (!allowBlank && value.isBlank()) {
        throw IllegalArgumentException("${T::class.java.simpleName} value cannot be blank.")
    }
    if (value.length < minLength) {
        throw IllegalArgumentException("'${value}' is less than minimum length for a ${T::class.java.simpleName}.")
    }
    if (value.length > maxLength) {
        throw IllegalArgumentException("'${value}' exceeds maximum length for a ${T::class.java.simpleName}.")
    }
    if (value.any { !isValid(it) }) {
        throw IllegalArgumentException("'${value}' is not a valid ${T::class.java.simpleName}. Value must be ${descriptor}.")
    }
    return value
}

inline fun <reified T : Any> validateLength(
    value: CharSequence,
    vararg validLengths: Int
): CharSequence {
    if (!validLengths.contains(value.length)) {
        throw IllegalArgumentException("'${value}' is not a valid ${T::class.java.simpleName}; length is incorrect.")
    }
    return value
}

fun Char.isValidSimpleAlpha(): Boolean =
    isLetterOrDigit() || isSpace()

fun Char.isValidSimpleAlpha(allowedSymbols: Iterable<Char>): Boolean =
    isValidSimpleAlpha() || (allowedSymbols.contains(this))

fun Char.isValidIdentifier(allowExtendedChars: Boolean = false): Boolean =
    isLetterOrDigit() || (allowExtendedChars && isUnderscoreOrHyphen())

fun Char.isValidNumeric(allowExtendedChars: Boolean = false): Boolean =
    isDigit() || (allowExtendedChars && isUnderscoreOrHyphen())

private fun Char.isSpace(): Boolean =
    (this == ' ')

private fun Char.isUnderscoreOrHyphen(): Boolean =
    (this == '_' || this == '-')