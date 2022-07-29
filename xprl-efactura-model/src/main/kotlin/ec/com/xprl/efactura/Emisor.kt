package ec.com.xprl.efactura

/**
 * Immutable representation of the emisor data for a comprobante electrónico.
 */
data class Emisor(
    val RUC: IdentityValue.RUC,
    val razónSocial: TextValue,
    val direccionMatriz: TextValue,
    val códigoEstablecimiento: CodeValue,
    val códigoPuntoEmisión: CodeValue,
    val direcciónEstablecimiento: TextValue? = null,
    val nombreComercial: TextValue? = null,
    val contribuyenteEspecial: NumericIdentifier? = null,
    val obligadoLlevarCompt: Boolean? = null
)

