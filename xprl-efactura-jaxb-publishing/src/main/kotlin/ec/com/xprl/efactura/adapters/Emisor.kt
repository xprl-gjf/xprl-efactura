package ec.com.xprl.efactura.adapters

/**
 * Immutable formatted representation of emisor data for a comprobante electrónico.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class Emisor(val src: ec.com.xprl.efactura.Emisor) {
    val RUC: String
        get() = src.RUC.value
    val razónSocial: String
        get() = src.razónSocial.value
    val direccionMatriz: String
        get() = src.direccionMatriz.value
    val códigoEstablecimiento: String
        get() = String.format("%03d", src.códigoEstablecimiento.value)
    val códigoPuntoEmisión: String
        get() = String.format("%03d", src.códigoPuntoEmisión.value)
    val direcciónEstablecimiento: String?
        get() = src.direcciónEstablecimiento?.value
    val nombreComercial: String?
        get() = src.nombreComercial?.value
    val contribuyenteEspecial: String?
        get() = src.contribuyenteEspecial?.value
    val obligadoLlevarCompt: Boolean?
        get() = src.obligadoLlevarCompt
}

