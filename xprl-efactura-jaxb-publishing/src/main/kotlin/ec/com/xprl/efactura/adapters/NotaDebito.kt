package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.NotaDebito
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a NotaDebito.
 */
@Suppress("MemberVisibilityCanBePrivate")
class NotaDebito(
    src: NotaDebito
) : ComprobanteElectronico(src) {

    val comprador: Comprador = Comprador(src.comprador)
    val debito: Debito = Debito(src.debito)


    class Debito(val src: NotaDebito.Debito) {
        val codDocModificado: String
            get() = String.format("%02d", src.documentoModificado.tipoDocumento.value)
        val numDocModificado: String
            get() = src.documentoModificado.id.toString()
        val fechaEmisionDocSustento: String
            get() = src.documentoModificado.fechaEmisionDocSustento.toDateString()
        val rise: String?
            get() = src.rise?.value

        val valores: Valores = Valores(src.valores)
        val pagos: List<Pago> = src.pagos.map { Pago(it) }
        val motivos: List<Motivo> = src.motivos.map { Motivo(it) }
    }


    class Valores(val src: NotaDebito.Valores) {
        val totalSinImpuestos: BigDecimal
            get() = src.totalSinImpuestos.toBigDecimal()
        val valorTotal: BigDecimal
            get() = src.valorTotal.toBigDecimal()
        val impuestos: List<Impuesto> = src.impuestos.map { (k, v) ->
            Impuesto(k, v)
        }
    }


    class Motivo(val src: NotaDebito.Motivo) {
        val razon: String
            get() = src.razon.value
        val valor: BigDecimal
            get() = src.valor.toBigDecimal()
    }
}
