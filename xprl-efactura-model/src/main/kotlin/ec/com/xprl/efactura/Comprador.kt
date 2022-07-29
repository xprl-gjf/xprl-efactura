package ec.com.xprl.efactura

/**
 * Immutable representation of the comprador data for a comprobante electrónico.
 */
data class Comprador(
    val identificación: IdentityValue,
    val razónSocial: TextValue,
    val dirección: TextValue? = null
)
