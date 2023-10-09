package uk.co.xprl.efactura

/**
 * Immutable representation of the proveedor data for a liquidación de compra.
 */
data class Proveedor(
    val identificación: IdentityValue,
    val razónSocial: TextValue,
    val dirección: TextValue? = null
)