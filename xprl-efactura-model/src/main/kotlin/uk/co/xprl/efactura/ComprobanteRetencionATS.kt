package uk.co.xprl.efactura

import kotlinx.datetime.LocalDate


/**
 * Immutable data representation of a ComprobanteRetencion ATS (schema version V2.0.0).
 */
class ComprobanteRetencionATS(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val sujeto: Sujeto,
    val valores: Valores,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
) : ComprobanteElectronicoBase<ComprobanteRetencionATS>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

    data class Sujeto(
        val tipoSujetoRetenido: TipoSujetoRetenido? = null,
        val identificación: IdentityValue,
        val razónSocial: TextValue,
        val parteRelacionada: Boolean
    )

    data class Valores(
        val periodoFiscal: LocalDate,
        val sustentos: List<SustentoATS>,
    )
}