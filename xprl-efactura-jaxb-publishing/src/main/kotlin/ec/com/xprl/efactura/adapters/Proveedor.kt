package ec.com.xprl.efactura.adapters

/**
 * Immutable formatted representation of comprador data for a liquidación de compra.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class Proveedor(val src: ec.com.xprl.efactura.Proveedor) {
    val tipoIdentificación: String
        get() = String.format("%02d", src.identificación.tipoIdentificacionCodigo)
    val identificación: String
        get() = src.identificación.value
    val razónSocial: String
        get() = src.razónSocial.value
    val dirección: String?
        get() = src.dirección?.value
}

