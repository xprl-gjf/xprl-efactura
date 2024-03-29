package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.ComprobanteDetalle
import java.math.BigDecimal

/**
 * Immutable formatted representation of comprobante detalle for a factura.
 */
@Suppress("MemberVisibilityCanBePrivate")
class ComprobanteDetalle(val src: ComprobanteDetalle) {
    val codigoPrincipal: String
        get() = src.codigoPrincipal.value
    val descripcion: String
        get() = src.descripcion.value
    val cantidad: BigDecimal
        get() = src.cantidad.toBigDecimal()
    val precioUnitario: BigDecimal
        get() = src.precioUnitario.toBigDecimal()
    val descuento: BigDecimal
        get() = src.descuento.toBigDecimal()
    val precioTotalSinImpuesto: BigDecimal
        get() = src.precioTotalSinImpuesto.toBigDecimal()
    val codigoAuxiliar: String? = src.codigoAuxiliar?.value

    val impuestos: List<DetalleImpuesto> = src.impuestos.map { (k, v) ->
        DetalleImpuesto(k, v)
    }
    val detallesAdicionales: Map<String, String>? = src.detallesAdicionales?.map{ (k, v) ->
        k.value to v.value
    }?.toMap()
}

