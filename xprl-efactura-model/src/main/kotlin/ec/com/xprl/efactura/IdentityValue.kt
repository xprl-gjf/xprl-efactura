package ec.com.xprl.efactura

private const val RUC_LENGTH = 13
private const val PASAPORTE_MAX_LENGTH = 14

/**
 * Abstract data type representación por un valor de identidad.
 */
sealed class IdentityValue(value: CharSequence) {
    val value: String = value.toString()

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as IdentityValue
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /* empty - included solely to support extensions */
    }

    /**
     * Representación de una identidad por RUC.
     */
    class RUC private constructor(id: CharSequence) : IdentityValue(id) {
        companion object {
            @JvmStatic fun from(value: CharSequence) = RUC(validate(value))
            @JvmStatic fun from(value: Long) = RUC(validate(value.toString()))
            @JvmStatic fun from(value: ULong) = RUC(validate(value.toString()))

            private fun validate(value: CharSequence) =
                validateNumeric<RUC>(value, RUC_LENGTH, RUC_LENGTH)
        }
    }

    /**
     * Representación de una identidad por Cédula.
     */
    class Cedula private constructor(id: CharSequence) : IdentityValue(id) {
        companion object {
            @JvmStatic fun from(value: CharSequence) = Cedula(validate(value))
            @JvmStatic fun from(value: Long) = Cedula(validate(value.toString()))
            @JvmStatic fun from(value: ULong) = Cedula(validate(value.toString()))

            private fun validate(value: CharSequence) =
                validateNumeric<Cedula>(value, IDENTIFIER_MAX_LENGTH)
        }
    }

    /**
     * Representación de una identidad por pasaporte.
     */
    class Pasaporte private constructor(id: CharSequence) : IdentityValue(id) {
        companion object {
            @JvmStatic fun from(value: CharSequence) = Pasaporte(validate(value))

            private fun validate(value: CharSequence) =
                validateIdentifier<Pasaporte>(value, PASAPORTE_MAX_LENGTH)
        }
    }

    /**
     * Representación de una identidad por 'identificación del exterior'.
     */
    class IdentificacionDelExterior private constructor(id: CharSequence) : IdentityValue(id) {
        companion object {
            @JvmStatic fun from(value: CharSequence) = IdentificacionDelExterior(validate(value))

            private fun validate(value: CharSequence) =
                validateIdentifier<IdentificacionDelExterior>(value, IDENTIFIER_MAX_LENGTH)
        }
    }

    /**
     * Representación de una identidad por un consumidor final.
     */
    object ConsumidorFinal : IdentityValue("9".repeat(13))
}


private inline fun <reified T: Any> validateNumeric(
    value: CharSequence,
    maxLength: Int,
) = validateCharSequence<T>(value, "numeric", minLength=0, maxLength, allowBlank = false) {
    it.isValidNumeric()
}

private inline fun <reified T: Any> validateNumeric(
    value: CharSequence,
    minLength: Int,
    maxLength: Int,
) = validateCharSequence<T>(value, "numeric", minLength, maxLength, allowBlank = false) {
    it.isValidNumeric()
}

private inline fun <reified T: Any> validateIdentifier(
    value: CharSequence,
    maxLength: Int,
    allowExtendedChars: Boolean = false,
) = validateCharSequence<T>(value, "alphanumeric id", maxLength, allowBlank = false) {
    it.isValidIdentifier(allowExtendedChars)
}

