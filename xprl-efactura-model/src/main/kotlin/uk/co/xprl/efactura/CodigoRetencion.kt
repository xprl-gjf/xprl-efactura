package uk.co.xprl.efactura

private const val CODIGO_RETENCION_MAX_LENGTH = 5

/**
 * Immutable representation of an alphanumeric code value for retenciones;
 * suitable for, e.g. [ImpuestoRetencionIdentidad.codigoPorcentaje].
 */
class CodigoRetencion private constructor (val value: String) {

    override fun toString() = value

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CodigoRetencion
        return other.value == this.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Maximum length of a [CodigoRetencion].
         */
        val MAX_LENGTH: Int
            @JvmStatic get() = CODIGO_RETENCION_MAX_LENGTH

        /**
         * Factory function to create a [CodigoRetencion] from a string.
         * @exception IllegalArgumentException the string value is not a valid [CodigoRetencion].
         */
        fun from(value: CharSequence) = CodigoRetencion(validate(value))

        /**
         * Factory function to create a [CodigoRetencion] from an integer.
         * @exception IllegalArgumentException the value is not a valid [CodigoRetencion].
         */
        fun from(value: Int) = CodigoRetencion(validate(value.toString()))
        /**
         * Factory function to create a [CodigoRetencion] from an unsigned integer.
         * @exception IllegalArgumentException the value is not a valid [CodigoRetencion].
         */
        fun from(value: UInt) = CodigoRetencion(validate(value.toString()))

        private fun validate(value: CharSequence) =
            validateCharSequence<CodigoRetencion>(
                value,
                "impuesto codigo retencion",
                CODIGO_RETENCION_MAX_LENGTH,
                allowBlank=false
            ) {
                it.isLetterOrDigit()
            }.toString()
    }
}