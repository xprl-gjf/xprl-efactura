@file:Suppress("MemberVisibilityCanBePrivate")

package ec.com.xprl.efactura.jaxb

import ec.com.xprl.efactura.*
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import java.io.StringWriter
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import javax.xml.stream.XMLOutputFactory


const val JAXB_UTF8_ENCODING = "UTF-8"

class JaxbComprobantePublisher<T: ComprobanteElectronico>(
    val ambiente: Ambiente,
    val validationEnabled: Boolean = true,
    val factory: JaxbComprobanteFactory = JaxbComprobanteFactoryImpl.default(ambiente)
) : ComprobantePublisher<T> {

    override fun publish(comprobante: T, version: SchemaVersion<T>): PublishedComprobante<T> {
        val (claveAcceso, jaxbComprobante, jaxbComprobanteClass) = factory.createJaxbComprobante(comprobante, version)

        if (validationEnabled) {
            validate(jaxbComprobanteClass, jaxbComprobante)
        }

        val xml = genXml(jaxbComprobanteClass, jaxbComprobante)
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

    private fun genXml(klass: Class<*>, jaxbComprobante: Any): String {
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
        return stringWriter.buffer.toString()
    }

    private companion object {
        val validatorFactory: ValidatorFactory by lazy {
            Validation.buildDefaultValidatorFactory()
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