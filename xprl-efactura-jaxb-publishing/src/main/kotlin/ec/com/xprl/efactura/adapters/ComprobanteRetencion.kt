package ec.com.xprl.efactura.adapters

import kotlinx.datetime.LocalDate
import ec.com.xprl.efactura.ComprobanteRetencion


/**
 * Immutable formatted data representation of a ComprobanteRetencion.
 */
@Suppress("MemberVisibilityCanBePrivate")
class ComprobanteRetencion(
    src: ComprobanteRetencion
) : ComprobanteElectronico(src) {

    val sujecto: Sujecto = Sujecto(src.sujecto)
    val valores: Valores = Valores(src.valores)


    class Sujecto(val src: ComprobanteRetencion.Sujecto) {
        val tipoIdentificación: String
            get() = String.format("%02d", src.identificación.tipoIdentificacionCodigo)
        val identificación: String
            get() = src.identificación.value
        val razónSocial: String
            get() = src.razónSocial.value
    }

    class Valores(val src: ComprobanteRetencion.Valores) {
        val periodoFiscal: String
            get() = src.periodoFiscal.toPeriodoFiscalDateString()
        val impuestos: List<ImpuestoRetencion> = src.impuestos.map { (k, v) ->
            ImpuestoRetencion(k, v)
        }
    }

}

private fun LocalDate.toPeriodoFiscalDateString() = toMonthAndYearDateString()
