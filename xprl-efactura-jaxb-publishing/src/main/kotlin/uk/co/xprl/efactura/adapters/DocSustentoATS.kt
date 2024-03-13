package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.DocSustentoATS

/**
 * Immutable formatted representation of a sustento for a ComprobanteRetencionATS (schema v2.0.0).
 */
class DocSustentoATS(
    val src: DocSustentoATS
) {
    val codDocSustento: String
        get() = String.format("%02d", src.tipoDocumento.codigo)
    val numDocSustento: String?
        get() = src.numDocSustento?.value
    val fechaEmisionDocSustento: String
        get() = src.fechaEmisionDocSustento.toDateString()
    val fechaRegistroContable: String?
        get() = src.fechaRegistroContable?.toDateString()
    val numAutDocSustento: String?
        get() = src.numAutDocSustento?.value
}