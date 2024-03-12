package uk.co.xprl.efactura

/**
 * A version value for a schema version.
 */
data class Version(
    val major: Int,
    val minor: Int,
    val patch: Int
) {
    override fun toString() = "${major}.${minor}.${patch}"
}

/**
 * Known schema versions for comprobantes electrónicos.
 */
sealed class SchemaVersion<T: ComprobanteElectronico>(val efacturaClass: Class<T>, val version: Version) {

    override fun toString() = "${efacturaClass.simpleName}_v${version}"

    /* Factura schema versions */
    object FacturaV100: SchemaVersion<Factura>(Factura::class.java, Version(1, 0, 0))
    object FacturaV110: SchemaVersion<Factura>(Factura::class.java, Version(1, 1, 0))
    // TODO: FacturaV200
    // TODO: FacturaV210

    /* NotaCredito schema versions */
    object NotaCreditoV100: SchemaVersion<NotaCredito>(NotaCredito::class.java, Version(1, 0, 0))
    object NotaCreditoV110: SchemaVersion<NotaCredito>(NotaCredito::class.java, Version(1, 1, 0))

    /* NotaDebito schema versions */
    object NotaDebitoV100: SchemaVersion<NotaDebito>(NotaDebito::class.java, Version(1, 0, 0))

    /* ComprobanteRetencion schema versions */
    object ComprobanteRetencionV100: SchemaVersion<ComprobanteRetencion>(ComprobanteRetencion::class.java, Version(1, 0, 0))
    object ComprobanteRetencionV200: SchemaVersion<ComprobanteRetencionATS>(ComprobanteRetencionATS::class.java, Version(2, 0, 0))

    /* GuiaRemision schema versions */
    object GuiaRemisionV100: SchemaVersion<GuiaRemision>(GuiaRemision::class.java, Version(1, 0, 0))
    object GuiaRemisionV110: SchemaVersion<GuiaRemision>(GuiaRemision::class.java, Version(1, 1, 0))

    /* LiquidacionCompra schema versions */
    object LiquidacionCompraV100: SchemaVersion<LiquidacionCompra>(LiquidacionCompra::class.java, Version(1, 0, 0))
    object LiquidacionCompraV110: SchemaVersion<LiquidacionCompra>(LiquidacionCompra::class.java, Version(1, 1, 0))
}