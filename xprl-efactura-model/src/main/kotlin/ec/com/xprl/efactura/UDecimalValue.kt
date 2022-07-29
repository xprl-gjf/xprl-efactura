package ec.com.xprl.efactura

import java.math.BigDecimal

/**
 * Immutable representation of an unsigned decimal value,
 * representing an amount of currency >= 0;
 * suitable for, e.g. [Factura.Totals.totalSinImpuestos].
 */
class UDecimalValue
    @Throws(IllegalArgumentException::class)
    constructor (value: DecimalValue) {

    @Throws(IllegalArgumentException::class, NumberFormatException::class)
    constructor(value: String): this(BigDecimal(value))

    @Throws(IllegalArgumentException::class)
    constructor(value: Int): this(BigDecimal(value))

    @Throws(IllegalArgumentException::class)
    constructor(value: Double): this(BigDecimal(value.toString()))  // constructing a BigDecimal from double
                                                                    // 'cam be somewhat unpredictable'
                                                                    // due to the binary representation of doubles;
                                                                    // therefore, for consistency, convert to a string
                                                                    // and parse the string

    val value: DecimalValue

    init { this.value = validate(value) }

    override fun toString() = value.toString()

    companion object {
        private val ZERO = BigDecimal(0)
        /**
         * Factory function to create an [UDecimalValue] from a BigDecimal.
         * @exception IllegalArgumentException the value is not a valid [UDecimalValue].
         */
        @JvmStatic fun from(value: BigDecimal) = UDecimalValue(validate(value))

        private fun validate(value: BigDecimal) =
            if (value < ZERO) {
                throw IllegalArgumentException("'${value}' is not a valid ${UDecimalValue::class.java.simpleName}. Value must be >= 0.")
            } else {
                value
            }
    }
}