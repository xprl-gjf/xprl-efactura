package uk.co.xprl.efactura.adapters

/**
 * Immutable formatted representation of 'pagoLocExt' and related fields for a ComprobanteRetencion esquema v2.0.0.
 */
@Suppress("MemberVisibilityCanBePrivate")
class PagoRetencionATS(
    val src: uk.co.xprl.efactura.PagoRetencionATS
) {
    private val pagoNoResidente = src as? uk.co.xprl.efactura.PagoRetencionATS.NoResidente

    val pagoLocExt: String
        get() = String.format("%02d", src.tipoPago.codigo)

    val tipoRegi: String?
        get() = pagoNoResidente?.let { String.format("%02d", it.tipoRegimen.codigo) }
    val paisEfecPago: String?
        get() = pagoNoResidente?.let { String.format("%03d", it.pais.value) }
    val aplicConvDobTrib: Boolean?
        get() = pagoNoResidente?.aplicConvDobTrib
    val pagExtSujRetNorLeg: Boolean?
        get() = pagoNoResidente?.pagExtSujRetNorLeg
    val pagRegFis: Boolean?
        get() = pagoNoResidente?.pagoRegFis
}