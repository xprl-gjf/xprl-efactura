package ec.com.xprl.efactura

typealias ClaveAcceso = CharSequence

/**
 * A comprobante electrónico that has been published to XML, with associated metadata.
 */
data class PublishedComprobante<T : ComprobanteElectronico>(
    val comprobante: T,
    val schemaVersion: SchemaVersion<T>,
    val ambiente: Ambiente,
    val tipoEmisión: TipoEmisión,
    val claveAcceso: ClaveAcceso,
    // TODO: replace String with a stream, or a producer of type ReceiveChannel<Char>
    val xml: CharSequence
)