package ec.com.xprl.efactura

import kotlinx.datetime.LocalDate

typealias InfoAdicional = Map<TextValue, TextValue>

/**
 * Interface shared by all types of comprobante electrónico.
 */
sealed interface ComprobanteElectronico {
    val secuencial: SecuencialValue
    val fechaEmision: LocalDate
    val emisor: Emisor
    val infoAdicional: InfoAdicional?
}

/**
 * Interface for all types of comprobante electrónico that can be published.
 */
sealed interface PublishableComprobante<T: ComprobanteElectronico> {
    fun publish(publisher: ComprobantePublisher<T>, version: SchemaVersion<T>): PublishedComprobante<T>
}

/**
 * Base class implementation for all types of comprobante electrónico.
 */
sealed class ComprobanteElectronicoBase<T : ComprobanteElectronico>(
    final override val secuencial: SecuencialValue,
    final override val fechaEmision: LocalDate,
    final override val emisor: Emisor,
    final override val infoAdicional: InfoAdicional? = null
): ComprobanteElectronico, PublishableComprobante<T> {

    /**
     * Publish this comprobante using the given publisher and schema version.
     */
    @Suppress("UNCHECKED_CAST")
    override fun publish(publisher: ComprobantePublisher<T>, version: SchemaVersion<T>): PublishedComprobante<T>
            = publisher.publish(this as T, version)
}
