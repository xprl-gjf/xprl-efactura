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
import javax.xml.XMLConstants
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


const val JAXB_UTF8_ENCODING = "UTF-8"

/*
 * Design trade-off:
 * Ideally, we want to perform Jakarta validation against the content of a [ComprobanteElectronico]
 * _with_ xml-encoding applied (i.e. & as &amp; > as &gt; < as &lt;) because this affects the length
 * of text strings.
 *
 * However, JAXB marshalling itself performs xml-encoding. So, if we have an XML-encoded
 * [ComprobanteElectronico], JAXB marshalling will result in double-encoding (i.e. &amp becomes &amp;amp;)
 *
 * Possible solutions:
 * 1) Ensure the XML-encoding has been applied to all text values in the [ComprobanteElectronico]
 * for purposes of validation. Disable XML-encoding in the JAXB marshaller.
 * See comments at the end of this file for an example of how to disable XML-encoding.
 * This allows precise validation, but runs the risk of failures in JAXB XML serialization if some
 * text value somewhere in the comprobante electrónico has _not_ already been encoded.
 *
 * 2) Store text values in the [ComprobanteElectronico] without XML encoding.
 * Note, however, that it remains possible in most cases to construct the [ComprobanteElectronico]
 * using text values that have originally been validated _with_ xml encoding applied
 * (see for example [ec.com.xprl.efactura.TextValue]).
 * Therefore, this slightly diminishes the usefulness of Jakarta validation but at least will not
 * break XML serialization.
 *
 * 3) Implement some solution using Jakarta validation 'ValueExtractor' classes and JAXB marshalling
 * overrides to store text values in such a manner that the validation is performed against the
 * XML-encoded value whilst JAXB marshals the original non-XML-encoded value.
 * Possible, but very complicated.
 *
 * 4) Abandon Jakarta validation and configure validation of the JAXBContext against the XSD.
 * Requires significant upstream changes in the sri-efactura-core and xprl-efactura libraries.
 *
 * Therefore, for now, we go with option 2). However, if necessary in future there might be
 * value in exploring option 3).
 *
 * (As an aside, experimental results indicate that SRI unfortunately performs data validation
 * against the xml-encoded string values, as opposed to performing xml-decoding before validation.
 * In other words, the SRI server may reject xml-encoded strings as being too long. On the other hand,
 * we cannot say whether this behaviour might change in the future...?)
 *
 */


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
        val writer = StringWriter()
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false)
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_UTF8_ENCODING)
        jaxbMarshaller.marshal(jaxbComprobante, writer)
        writer.flush()

        return if (formattedXml) {
            formatXml(writer.buffer)
        } else {
            writer.buffer
        }
    }

    private companion object {
        val validatorFactory: ValidatorFactory by lazy {
            Validation.buildDefaultValidatorFactory()
        }

        private fun formatXml(xml: CharSequence): CharSequence {
            val tf = TransformerFactory.newInstance()
            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            val t: Transformer = tf.newTransformer()
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


/*
 * The following is an example of how to disable XML-encoding of characters in the JAXB marshaller:
 *
 * ===============
 * build.gradle.kts:
 *
 * dependencies {
 *     implementation("com.sun.xml.bind:jaxb-core:4.0.0")
 *     ...
 * }
 *
 * ================
 * JaxbComprobantePublisher.kt:
 *
 * private fun genXml(...):
 *     ...
 *     val jaxbMarshaller = jaxbContext.createMarshaller()
 *     jaxbMarshaller.setProperty(
 *         "org.glassfish.jaxb.marshaller.CharacterEscapeHandler",
 *         NullCharacterEscapeHandler()
 *     );
 *     ...
 *
 * // CharacterEscapeHandler that does not perform any character escaping
 * class NullCharacterEscapeHandler : org.glassfish.jaxb.core.marshaller.CharacterEscapeHandler {
 *     override fun escape(ch: CharArray?, start: Int, length: Int, isAttVal: Boolean, out: Writer?) {
 *         out?.write(ch, start, length)   // write the contents of the CharArray unchanged
 *     }
 * }
 */
