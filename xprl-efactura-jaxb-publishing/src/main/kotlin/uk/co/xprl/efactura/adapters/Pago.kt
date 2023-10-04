package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.Pago
import uk.co.xprl.efactura.PagoATS
import uk.co.xprl.efactura.UDecimalValue
import java.math.BigDecimal

/**
 * Immutable formatted representation of pago data for a comprobante electrónico.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Pago(
    private val srcFormaPago: Int,
    private val srcTotal: UDecimalValue,
    private val srcPlazo: Pago.Plazo? = null,
) {
    constructor(src: Pago) : this(src.formaPago.formaDePagoCodigo, src.total, src.plazo)
    constructor(src: PagoATS) : this(src.formaPago.value, src.total)

    val formaPago: String
        get() = String.format("%02d", srcFormaPago)
    val total: BigDecimal
        get() = srcTotal.toBigDecimal()
    val plazoValue: BigDecimal?
        get() = srcPlazo?.value?.toBigDecimal()
    val plazoUnidadTiempo: String?
        get() = srcPlazo?.unidadTiempo?.jaxbString
}