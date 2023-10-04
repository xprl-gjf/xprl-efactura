package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.ImpuestoDetalle
import uk.co.xprl.efactura.ImpuestoIdentidad
import java.math.BigDecimal

/**
 * Immutable formatted representation of an ImpuestoDocSustento for a ComprobanteRetencion esquema v2.0.0.
 */
@Suppress("MemberVisibilityCanBePrivate")
class ImpuestoDocSustento(
    val srcIdentidad: ImpuestoIdentidad,
    val srcValor: ImpuestoDetalle
) {
    val codImpuestoDocSustento: String
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