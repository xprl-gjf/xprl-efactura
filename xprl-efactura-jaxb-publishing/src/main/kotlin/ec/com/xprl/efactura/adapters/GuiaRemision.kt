package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.GuiaRemision
import java.math.BigDecimal

/**
 * Immutable formatted data representation of a GuiaRemision.
 */
@Suppress("MemberVisibilityCanBePrivate")
class GuiaRemision(
    src: GuiaRemision
) : ComprobanteElectronico(src) {

    val remision: Remision = Remision(src.remision)
    val destinatarios: List<Destinatario> = src.destinatarios.map { Destinatario(it) }

    class Remision(val src: GuiaRemision.Remision) {
        val transportista = Transportista(src.transportista)

        val dirPartida: String
            get() = src.dirPartida.value
        val dirEstablecimiento: String?
            get() = src.dirEstablecimiento?.value
        val contribuyenteEspecial: String?
            get() = src.contribuyenteEspecial?.value
        val obligadoLlevarCompt: Boolean?
            get() = src.obligadoLlevarCompt
        val rise: String?
            get() = src.rise?.value
    }

    class Transportista(val src: GuiaRemision.Transportista) {
        val tipoIdentificación: String
            get() = String.format("%02d", src.identificación.tipoIdentificacionCodigo)
        val identificación: String
            get() = src.identificación.value
        val razónSocial: String
            get() = src.razónSocial.value
        val fechaIniTransporte: String
            get() = src.fechaIniTransporte.toDateString()
        val fechaFinTransporte: String
            get() = src.fechaFinTransporte.toDateString()
        val placa: String
            get() = src.placa.value
    }


    class Destinatario(val src: GuiaRemision.Destinatario) {
        val identificación: String
            get() = src.identificación.value
        val razónSocial: String
            get() = src.razónSocial.value
        val dirección: String
            get() = src.dirección.value
        val motivoTranslado: String
            get() = src.motivoTranslado.value
        val docAduaneroUnico: String?
            get() = src.docAduaneroUnico?.value
        val codEstabDestino: String?
            get() = src.codEstabDestino?.let { String.format("%03d", it.value) }
        val ruta: String?
            get() = src.ruta?.value
        val codDocSustento: String?
            get() = src.documentoSustento?.let { String.format("%02d", it.tipoDocumento.value) }
        val numDocSustento: String?
            get() = src.documentoSustento?.id?.toString()
        val fechaEmisionDocSustento: String?
            get() = src.documentoSustento?.fechaEmisionDocSustento?.toDateString()
        val numAutDocSustento: String?
            get() = src.numAutDocSustento?.value

        val detalles: List<DestinatarioDetalle> = src.detalles.map { DestinatarioDetalle(it) }
    }

    class DestinatarioDetalle(val src: GuiaRemision.DestinatarioDetalle) {
        val descripcion: String
            get() = src.descripcion.value
        val cantitad: BigDecimal
            get() = src.cantitad.toBigDecimal()
        val codigoInterno: String?
            get() = src.codigoInterno?.value
        val codigoAdicional: String?
            get() = src.codigoAdicional?.value

        val detallesAdicionales: Map<String, String>? = src.detallesAdicionales?.map { (k, v) ->
            k.value to v.value
        }?.toMap()
    }
}
