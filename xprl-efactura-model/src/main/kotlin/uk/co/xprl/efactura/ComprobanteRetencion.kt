package uk.co.xprl.efactura

import kotlinx.datetime.LocalDate


/**
 * Immutable data representation of a ComprobanteRetencion.
 */
class ComprobanteRetencion(
    secuencial: SecuencialValue,
    fechaEmision: LocalDate,
    emisor: Emisor,
    val sujecto: Sujecto,
    val valores: Valores,
    maquinaFiscal: MaquinaFiscal? = null,
    infoAdicional: InfoAdicional? = null
) : ComprobanteElectronicoBase<ComprobanteRetencion>(secuencial, fechaEmision, emisor, maquinaFiscal, infoAdicional) {

    data class Sujecto(
        val identificación: IdentityValue,
        val razónSocial: TextValue,
    )

    data class Valores(
        val periodoFiscal: LocalDate,
        val impuestos: Map<ImpuestoRetencionIdentidad, ImpuestoRetencion>,
    )

    data class ImpuestoRetencion(
        val valor: ImpuestoRetencionValor,
        val documentoSustento: DocSustento? = null
    )
}
