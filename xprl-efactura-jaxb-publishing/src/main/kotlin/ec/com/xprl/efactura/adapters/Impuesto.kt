package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.ImpuestoIdentidad
import ec.com.xprl.efactura.ImpuestoLiquidacionTotal
import ec.com.xprl.efactura.ImpuestoTotal
import java.math.BigDecimal

/**
 * Immutable formatted representation of an impuesto for a factura.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Impuesto(
    val srcIdentidad: ImpuestoIdentidad,
    val srcValor: ImpuestoTotal
) {
    val codigo: String
        get() = srcIdentidad.tipoImpuesto.tipoImpuestoCodigo.toString()
    val codigoPorcentaje: String
        get() = srcIdentidad.codigoPorcentaje.toString()
    val baseImponible: BigDecimal
        get() = srcValor.baseImponible.toBigDecimal()
    val valor: BigDecimal
        get() = srcValor.valor.toBigDecimal()
    val descuentoAdicional: BigDecimal?
        get() = srcValor.descuentoAdicional?.toBigDecimal()
    val valorDevolucionIva: BigDecimal?
        get() = srcValor.valorDevolucionIva?.toBigDecimal()
}

/**
 * Immutable formatted representation of an impuesto for a liquidacion de compra.
 */
@Suppress("MemberVisibilityCanBePrivate")
class ImpuestoLiquidacion(
    val srcIdentidad: ImpuestoIdentidad,
    val srcValor: ImpuestoLiquidacionTotal
) {
    val codigo: String
        get() = srcIdentidad.tipoImpuesto.tipoImpuestoCodigo.toString()
    val codigoPorcentaje: String
        get() = srcIdentidad.codigoPorcentaje.toString()
    val baseImponible: BigDecimal
        get() = srcValor.baseImponible.toBigDecimal()
    val valor: BigDecimal
        get() = srcValor.valor.toBigDecimal()
    val descuentoAdicional: BigDecimal?
        get() = srcValor.descuentoAdicional?.toBigDecimal()
    val tarifa: BigDecimal?
        get() = srcValor.tarifa?.toBigDecimal()
}