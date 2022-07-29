package ec.com.xprl.efactura.builders.remision

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.CompositeBuilder
import ec.com.xprl.efactura.builders.requires
import ec.com.xprl.efactura.builders.requiresNotEmpty

/**
 * Mutable builder for [GuiaRemision.Destinatario].
 */
class DestinatarioBuilder : CompositeBuilder<DestinatarioBuilder, GuiaRemision.Destinatario>(
    GuiaRemision.Destinatario::class.java,
    innerBuilderProperties = { builder ->
        builder.detalles ?: emptyList()
    },
    requires("identificación") { it.identificación },
    requires("razónSocial") { it.razónSocial },
    requires("dirección") { it.dirección },
    requires("motivoTranslado") { it.motivoTranslado },
    requiresNotEmpty("detalles") { it.detalles }
) {
    private var identificación: ShortTextValue? = null
    private var razónSocial: TextValue? = null
    private var dirección: TextValue? = null
    private var motivoTranslado: TextValue? = null
    private var detalles: List<DestinatarioDetalleBuilder>? = null
    private var docAduaneroUnico: ShortTextValue? = null
    private var codEstabDestino: CodeValue? = null
    private var ruta: TextValue? = null
    private var docSustento: DocSustento?= null
    private var numAutDocSustento: DocAutorizacionValue? = null

    fun setIdentificacion(value: ShortTextValue) = apply { identificación = value }
    fun setRazonSocial(value: TextValue) = apply { razónSocial = value }
    fun setDireccion(value: TextValue) = apply { dirección = value }
    fun setMotivoTranslado(value: TextValue) = apply { motivoTranslado = value }
    fun setDetalles(value: List<DestinatarioDetalleBuilder>) = apply { detalles = value }
    fun updateDetalles(value: List<DestinatarioDetalleBuilder>) = apply {
        detalles = if (detalles == null ) { value } else { detalles!! + value }
    }
    fun setDocAduaneroUnico(value: ShortTextValue?) = apply { docAduaneroUnico = value }
    fun setCodEstabDestino(value: CodeValue?) = apply { codEstabDestino = value }
    fun setRuta(value: TextValue?) = apply { ruta = value }
    fun setDocSustento(value: DocSustento?) = apply { docSustento = value }
    fun setNumAutDocSustento(value: DocAutorizacionValue?) = apply { numAutDocSustento = value }

    operator fun plus(other: DestinatarioBuilder) = merge(other)
    fun merge(other: DestinatarioBuilder) = apply {
        other.identificación?.let { setIdentificacion(it) }
        other.razónSocial?.let { setRazonSocial(it) }
        other.dirección?.let { setDireccion(it) }
        other.motivoTranslado?.let { setMotivoTranslado(it) }
        other.detalles?.let { updateDetalles(it) }
        other.docAduaneroUnico?.let { setDocAduaneroUnico(it) }
        other.codEstabDestino?.let { setCodEstabDestino(it) }
        other.ruta?.let { setRuta(it) }
        other.docSustento?.let { setDocSustento(it) }
        other.numAutDocSustento?.let { setNumAutDocSustento(it) }
    }

    override fun validatedBuild() = GuiaRemision.Destinatario(
        identificación!!,
        razónSocial!!,
        dirección!!,
        motivoTranslado!!,
        detalles!!.map { it.build() },
        docAduaneroUnico,
        codEstabDestino,
        ruta,
        docSustento,
        numAutDocSustento
    )
}

/*
    data class Destinatario(
        val identificación: ShortTextValue,
        val razónSocial: TextValue,
        val dirección: TextValue,
        val motivoTranslado: TextValue,
        val detalles: List<DestinatarioDetalle>,
        val docAduaneroUnico: ShortTextValue? = null,
        val codEstabDestino: CodeValue? = null,
        val ruta: TextValue? = null,
        val docSustento: DocSustento? = null,
        // number, 10 or 37 or 49 digits
        val numAutDocSustento: DocAutorizacionValue? = null
    )
 */