package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.ImpuestoRetencionIdentidad
import uk.co.xprl.efactura.ImpuestoRetencionValor
import java.math.BigDecimal

/**
 * Immutable formatted representation of a Retencion for a ComprobanteRetencion esquema v2.0.0.
 */
@Suppress("MemberVisibilityCanBePrivate")
class RetencionATS(
    val srcIdentidad: ImpuestoRetencionIdentidad,
    val srcValor: ImpuestoRetencionValor
) {
    val codigo: String
        get() = srcIdentidad.tipoImpuesto.codigo.toString()
    val codigoRetencion: String
        get() = srcIdentidad.codigoPorcentaje.value
    val baseImponible: BigDecimal
        get() = srcValor.baseImponible.toBigDecimal()
    val porcentajeRetener: BigDecimal
        get() = srcValor.porcentajeRetener.toBigDecimal()
    val valorRetenido: BigDecimal
        get() = srcValor.valorRetenido.toBigDecimal()

    val dividendos: DividendoDetalle?
        get() = srcValor.dividendos?.let { DividendoDetalle(it) }


    class DividendoDetalle(
        val src: ImpuestoRetencionValor.DividendoDetalle
    ) {
        val fechaPagoDiv: String
            get() = src.fechaPago.toDateString()
        val imRentaSoc: BigDecimal
            get() = src.imRentaSoc.toBigDecimal()
        val ejerFisUtDiv: String
            get() = String.format("%04d", src.ejerFisUtDiv)
    }
}