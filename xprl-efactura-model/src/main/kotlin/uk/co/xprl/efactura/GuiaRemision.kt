package uk.co.xprl.efactura

import kotlinx.datetime.LocalDate

/**
 * Immutable data representation of a Guia de Remision.
 */
class GuiaRemision(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val remision: Remision,
    val destinatarios: List<Destinatario>,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
) : ComprobanteElectronicoBase<GuiaRemision>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

    data class Remision(
        val dirPartida: TextValue,
        val transportista: Transportista,
        val dirEstablecimiento: TextValue? = null,
        val contribuyenteEspecial: NumericIdentifier? = null,
        val obligadoLlevarCompt: Boolean? = null,
        val rise: Rise? = null
    )

    data class Transportista(
        val identificación: IdentityValue.RUC,
        val razónSocial: TextValue,
        val fechaIniTransporte: LocalDate,
        val fechaFinTransporte: LocalDate,
        val placa: ShortTextValue,
    )

    data class Destinatario(
        val identificación: ShortTextValue,
        val razónSocial: TextValue,
        val dirección: TextValue,
        val motivoTranslado: TextValue,
        val detalles: List<DestinatarioDetalle>,
        val docAduaneroUnico: ShortTextValue? = null,
        val codEstabDestino: CodeValue? = null,
        val ruta: TextValue? = null,
        val documentoSustento: DocSustento? = null,
        // number, 10 or 37 or 49 digits
        val numAutDocSustento: DocAutorizacionValue? = null
    )

    data class DestinatarioDetalle(
        val descripcion: TextValue,
        val cantitad: UDecimalValue,
        val codigoInterno: AlphanumericCodeValue? = null,
        val codigoAdicional: AlphanumericCodeValue? = null,
        val detallesAdicionales: DetalleAdicional? = null
    )
}
