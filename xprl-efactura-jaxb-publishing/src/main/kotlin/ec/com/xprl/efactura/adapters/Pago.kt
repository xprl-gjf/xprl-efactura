package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.Pago
import java.math.BigDecimal

/**
 * Immutable formatted representation of pago data for a comprobante electrónico.
 */
@Suppress("MemberVisibilityCanBePrivate")
internal class Pago(val src: Pago) {
    val formaPago: String
        get() = String.format("%02d", src.formaPago.formaDePagoCodigo)
    val total: BigDecimal
        get() = src.total.toBigDecimal()
    val plazoValue: BigDecimal?
        get() = src.plazo?.value?.toBigDecimal()
    val plazoUnidadTiempo: String?
        get() = src.plazo?.unidadTiempo?.jaxbString
}