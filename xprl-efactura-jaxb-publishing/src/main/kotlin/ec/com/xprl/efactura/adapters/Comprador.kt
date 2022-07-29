package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.Comprador

/**
 * Immutable formatted representation of comprador data for a comprobante electrónico.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class Comprador(val src: Comprador) {
    val tipoIdentificación: String
        get() = String.format("%02d", src.identificación.tipoIdentificacionCodigo)
    val identificación: String
        get() = src.identificación.value
    val razónSocial: String
        get() = src.razónSocial.value
    val dirección: String?
        get() = src.dirección?.value
}
