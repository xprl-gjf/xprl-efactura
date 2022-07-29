package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.ImpuestoDetalle
import ec.com.xprl.efactura.ImpuestoIdentidad
import java.math.BigDecimal

/**
 * Immutable formatted representation of a comprobante detalle impuesto for a factura.
 */
@Suppress("MemberVisibilityCanBePrivate")
class DetalleImpuesto(
    val srcIdentidad: ImpuestoIdentidad,
    val srcValor: ImpuestoDetalle
) {
    val codigo: String
        get() = srcIdentidad.tipoImpuesto.tipoImpuestoCodigo.toString()
    val codigoPorcentaje: String
        get() = srcIdentidad.codigoPorcentaje.toString()
    val tarifa: BigDecimal
        get() = srcValor.tarifa.toBigDecimal()
    val baseImponible: BigDecimal
        get() = srcValor.baseImponible.toBigDecimal()
    val valor: BigDecimal
        get() = srcValor.valor.toBigDecimal()
}