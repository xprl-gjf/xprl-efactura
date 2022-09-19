@file:Suppress("MemberVisibilityCanBePrivate")

package ec.com.xprl.efactura.jaxb

import ec.com.xprl.efactura.*
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import java.io.StringReader
import java.io.StringWriter
import javax.xml.stream.XMLOutputFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


const val JAXB_UTF8_ENCODING = "UTF-8"

class JaxbComprobantePublisher<T: ComprobanteElectronico>(
    val ambiente: Ambiente,
    val validationEnabled: Boolean = true,
    val formattedXml: Boolean = false,
    val factory: JaxbComprobanteFactory = JaxbComprobanteFactoryImpl.default(ambiente)
) : ComprobantePublisher<T> {

    override fun publish(comprobante: T, version: SchemaVersion<T>): PublishedComprobante<T> {
        val (claveAcceso, jaxbComprobante, jaxbComprobanteClass) = factory.createJaxbComprobante(comprobante, version)

        if (validationEnabled) {
            validate(jaxbComprobanteClass, jaxbComprobante)
        }

        val xml = genXml(jaxbComprobanteClass, jaxbComprobante, formattedXml)
        return PublishedComprobante(
            comprobante = comprobante,
            schemaVersion = version,
            ambiente = ambiente,
            tipoEmisión = TipoEmisión.NORMAL,
            claveAcceso = claveAcceso,
            xml = xml
        )
    }

    private fun validate(klass: Class<*>, jaxbComprobante: Any) {
        val validator = validatorFactory.validator
        val violations = validator.validate(jaxbComprobante)
        if (violations.isNotEmpty()) {
            throw JaxbValidationException(klass, violations)
        }
    }

    private fun genXml(klass: Class<*>, jaxbComprobante: Any, formattedXml: Boolean): CharSequence {
        val jaxbContext = JAXBContext.newInstance(klass)
        val jaxbMarshaller = jaxbContext.createMarshaller()
        val stringWriter = StringWriter()
        val outputFactory = XMLOutputFactory.newDefaultFactory()
        // using XmlStreamWriter ensures that characters '&<>' are HTML-encoded
        val writer = outputFactory.createXMLStreamWriter(stringWriter)
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false)
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_UTF8_ENCODING)
        jaxbMarshaller.marshal(jaxbComprobante, writer)
        writer.flush()

        return if (formattedXml) {
            formatXml(stringWriter.buffer)
        } else {
            stringWriter.buffer
        }
    }

    private companion object {
        val validatorFactory: ValidatorFactory by lazy {
            Validation.buildDefaultValidatorFactory()
        }

        private fun formatXml(xml: CharSequence): CharSequence {
            val t: Transformer = TransformerFactory.newInstance().newTransformer()
            t.setOutputProperty(OutputKeys.INDENT, "yes")
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            val out = StringWriter()
            t.transform(StreamSource(StringReader(xml.toString())), StreamResult(out))
            return out.toString()
        }
    }
}


class JaxbValidationException internal constructor (klass: Class<*>, violations: Iterable<ConstraintViolation<*>>) : Exception(message(klass, violations)) {
    companion object {
        fun message(klass: Class<*>, violations: Iterable<ConstraintViolation<*>>) = violations.joinToString(
            "\n\t",
            "Validation errors for object of type '${klass.name}':\n\t"
        ) { it.formattedError }
    }
}

private val ConstraintViolation<*>.formattedError: String
    get() = "$propertyPath value $formattedErrorValue: $message"

private val ConstraintViolation<*>.formattedErrorValue: String
    get() = invalidValue?.let {
        "'$invalidValue'"
    } ?: "NULL"