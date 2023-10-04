package uk.co.xprl.efactura

/**
 * Immutable representation of pago data for a comprobante de retencion de ATS (esquema v2.0.0).
 */
data class PagoATS(
    val formaPago: FormaDePagoATS,
    val total: UDecimalValue
)