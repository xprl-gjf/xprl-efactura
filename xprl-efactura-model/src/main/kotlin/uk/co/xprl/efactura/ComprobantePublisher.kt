package uk.co.xprl.efactura

/**
 * Interface for a publisher that may be used to generate the
 * published form of a comprobante electrónico.
 *
 * The published form of a comprobante electrónico includes, most importantly,
 * the XML representation of the comprobante and the clave acceso.
 *
 * @see [PublishedComprobante]
 */
interface ComprobantePublisher<T : ComprobanteElectronico> {
    fun publish(comprobante: T, version: SchemaVersion<T>): PublishedComprobante<T>
}