package ec.com.xprl.efactura.jaxb

import ec.com.xprl.efactura.ComprobanteElectronico
import ec.com.xprl.efactura.PublishedComprobante
import ec.com.xprl.efactura.XAdESBESSignature
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.XMLConstants
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class ComprobanteFirmador(
    private val firma: ByteArray,
    private val contrasena: String
) {
    private val firmador: XAdESBESSignature by lazy {
        XAdESBESSignature()
    }

    @Throws(ComprobanteFirmaException::class)
    fun <T: ComprobanteElectronico> firmarComprobante(src: PublishedComprobante<T>): PublishedComprobante<T> {
        val toSign: ByteArray = src.xml.toString().encodeToByteArray()
        val doc = try {
            firmador.firmarDocumento(
                toSign,
                firma,
                contrasena,
                "contenido comprobante")
        } catch (e: Exception) {
            throw ComprobanteFirmaException("Failed to sign comprobante.", e)
        }

        val resultXml = try {
            doc.transformToString()
        } catch (e: TransformerException) {
            throw ComprobanteFirmaException("Failed to write comprobante to XML string.", e)
        }

        return PublishedComprobante(
            src.comprobante,
            src.schemaVersion,
            src.ambiente,
            src.tipoEmisión,
            src.claveAcceso,
            resultXml
        )
    }
}

class ComprobanteFirmaException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}

@kotlin.jvm.Throws(TransformerException::class)
private fun Document.transformToString(): String {
    val domSource = DOMSource(this)
    val writer = StringWriter()
    val result = StreamResult(writer)
    val tf = TransformerFactory.newDefaultInstance()
    // security-conscious settings:
    // tf.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true)
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    val transformer = tf.newTransformer()
    transformer.transform(domSource, result)
    return writer.toString()
}