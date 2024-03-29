package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.Factura
import uk.co.xprl.efactura.ImpuestoRetencionIvaPresuntivoYRenta
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a Factura.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Factura(
    src: Factura
) : ComprobanteElectronico(src) {

    val comprador: Comprador = Comprador(src.comprador)
    val valores: Valores = Valores(src.valores)
    val detalles: List<ComprobanteDetalle> = src.detalles.map { ComprobanteDetalle(it) }
    val reembolso: Reembolso? = src.reembolso?.let { Reembolso(it) }
    val reembolsoDetalles: List<ReembolsoDetalle>? = src.reembolsoDetalles?.map { ReembolsoDetalle(it) }
    val retenciones: List<Retencion>? = src.retenciones?.map { (k, v) ->
        Retencion(k, v)
    }
    val tipoNegociable: TipoNegociable? = src.tipoNegociable?.let { TipoNegociable(it) }


    class Valores(val src: Factura.Valores) {
        val totals: Totals = Totals(src.totals)
        val pagos: List<Pago> = src.pagos.map { Pago(it) }

        val propina: BigDecimal
            get() = src.propina?.toBigDecimal() ?: BigDecimal(0)
        val moneda: String
            get() = src.moneda.toString()
        val retIva: BigDecimal?
            get() = src.retIva?.toBigDecimal()
        val retRenta: BigDecimal?
            get() = src.retRenta?.toBigDecimal()
    }


    class Totals(val src: Factura.Totals) {
        val totalConImpuestos: List<Impuesto> = src.totalConImpuestos.map { (k, v) ->
            Impuesto(k, v)
        }
        val totalSinImpuestos: BigDecimal
            get() = src.totalSinImpuestos.toBigDecimal()
        val totalDescuento: BigDecimal
            get() = src.totalDescuento.toBigDecimal()
        val importeTotal: BigDecimal
            get() = src.importeTotal.toBigDecimal()
    }


    class Retencion(
        val srcIdentidad: ImpuestoRetencionIvaPresuntivoYRenta,
        val srcValor: Factura.Retencion
    ) {
        val codigo: String
            get() = srcIdentidad.tipoImpuesto.codigo.toString()
        val codigoPorcentaje: String
            get() = srcIdentidad.codigoPorcentaje.toString()
        val tarifa: BigDecimal
            get() = srcValor.tarifa.toBigDecimal()
        val valor: BigDecimal
            get() = srcValor.valor.toBigDecimal()
    }


    class TipoNegociable(
        var src: Factura.TipoNegociable
    ) {
        val correo: String
            get() = src.correo.value
    }
}
