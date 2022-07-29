package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.ComprobanteRetencion
import ec.com.xprl.efactura.ImpuestoRetencionIdentidad
import java.math.BigDecimal

/**
 * Immutable formatted representation of an impuesto for a comprobante retención.
 */
class ImpuestoRetencion(
    val srcIdentidad: ImpuestoRetencionIdentidad,
    val srcValor: ComprobanteRetencion.ImpuestoRetencion
) {
    val codigo: String
        get() = srcIdentidad.tipoImpuesto.codigo.toString()
    val codigoRetencion: String
        get() = srcIdentidad.codigoPorcentaje.toString()
    val baseImponible: BigDecimal
        get() = srcValor.valor.baseImponible.toBigDecimal()
    val porcentajeRetener: BigDecimal
        get() = srcValor.valor.porcentajeRetener.toBigDecimal()
    val valorRetenido: BigDecimal
        get() = srcValor.valor.valorRetenido.toBigDecimal()

    val codDocSustento: String?
        get() = srcValor.documentoSustento?.let { String.format("%02d", it.tipoDocumento.value) }
    val numDocSustento: String?
        get() = srcValor.documentoSustento?.id?.toString()
    val fechaEmisionDocSustento: String?
        get() = srcValor.documentoSustento?.fechaEmisionDocSustento?.toDateString()

}