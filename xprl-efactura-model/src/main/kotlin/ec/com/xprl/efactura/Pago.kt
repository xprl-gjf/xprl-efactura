package ec.com.xprl.efactura

/**
 * Immutable representation of pago data for a comprobante electrónico.
 */
data class Pago(
    val formaPago: FormaDePago,
    val total: UDecimalValue,
    val plazo: Plazo? = null
) {
    data class Plazo(
        val value: UDecimalValue,
        val unidadTiempo: UnidadTiempo
    ) {
        @Suppress("unused")
        enum class UnidadTiempo {
            DIAS,
            SEMANAS,
            MESES,
            ANOS
        }
    }
}