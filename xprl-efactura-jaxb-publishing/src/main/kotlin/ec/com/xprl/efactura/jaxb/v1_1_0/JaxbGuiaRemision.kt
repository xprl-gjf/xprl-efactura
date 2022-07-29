package ec.com.xprl.efactura.jaxb.v1_1_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.GuiaRemision as XprlGuiaRemision
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.remision.v1_1_0.GuiaRemision as GuiaRemision_1_1_0

/*
 * The only difference between schema v1.0.0 and v1.1.0 is the numerical precision
 * of the 'cantidad' field:
 *
      <xsd:simpleType name="cantidad">
          <xsd:restriction base="xsd:decimal">
 -            <xsd:totalDigits value="14"/>
 -            <xsd:fractionDigits value="2"/>
 +            <xsd:totalDigits value="18"/>
 +            <xsd:fractionDigits value="6"/>
              <xsd:minInclusive value="0"/>
          </xsd:restriction>
      </xsd:simpleType>
 */

internal fun createGuiaRemision(
    guiaRemision: XprlGuiaRemision,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = GuiaRemision_1_1_0().apply {
    val formatted = GuiaRemision(guiaRemision)
    id = COMPROBANTE_ID
    version = "1.1.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoGuiaRemision = createInfoGuiaRemision(formatted)
    destinatarios = createDestinatarios(formatted)
}

private fun createInfoTributaria(
    remision: GuiaRemision,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.remision.v1_1_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = remision.secuencial
    this.codDoc = remision.codDoc

    remision.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoGuiaRemision(
    remision: GuiaRemision
) = ec.gob.sri.remision.v1_1_0.GuiaRemision.InfoGuiaRemision().apply {
    remision.remision.let {
        dirEstablecimiento = it.dirEstablecimiento
        dirPartida = it.dirPartida
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.remision.v1_1_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.remision.v1_1_0.ObligadoContabilidad.NO
        }
        rise = it.rise
    }
    remision.remision.transportista.let {
        razonSocialTransportista = it.razónSocial
        tipoIdentificacionTransportista = it.tipoIdentificación
        rucTransportista = it.identificación
        placa = it.placa
    }
}

private fun createDestinatarios(
    remision: GuiaRemision
) = ec.gob.sri.remision.v1_1_0.GuiaRemision.Destinatarios().apply {
    with(destinatario) {
        remision.destinatarios.map {
            createDestinatario(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createDestinatario(
    destinatario: GuiaRemision.Destinatario
) = ec.gob.sri.remision.v1_1_0.Destinatario().apply {
    identificacionDestinatario = destinatario.identificación
    razonSocialDestinatario = destinatario.razónSocial
    dirDestinatario = destinatario.dirección
    motivoTraslado = destinatario.motivoTranslado
    docAduaneroUnico = destinatario.docAduaneroUnico
    codEstabDestino = destinatario.codEstabDestino
    ruta = destinatario.ruta
    codDocSustento = destinatario.codDocSustento
    numDocSustento = destinatario.numDocSustento
    numAutDocSustento = destinatario.numAutDocSustento
    fechaEmisionDocSustento = destinatario.fechaEmisionDocSustento
    detalles = createDestinatarioDetalles(destinatario.detalles)
}

private fun createDestinatarioDetalles(detalles: Iterable<GuiaRemision.DestinatarioDetalle>) =
    ec.gob.sri.remision.v1_1_0.Destinatario.Detalles().apply {
    with (detalle) {
        detalles.map {
            createDestinatarioDetalle(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createDestinatarioDetalle(
    detalle: GuiaRemision.DestinatarioDetalle
) = ec.gob.sri.remision.v1_1_0.Detalle().apply {
    codigoInterno = detalle.codigoInterno
    codigoAdicional = detalle.codigoAdicional
    descripcion = detalle.descripcion
    cantidad = detalle.cantitad
    detalle.detallesAdicionales?.let {
        detallesAdicionales = createDestinatarioDetallesAdicionales(it)
    }
}

private fun createDestinatarioDetallesAdicionales(detalles: Iterable<DetalleAdicionale>) =
    ec.gob.sri.remision.v1_1_0.Detalle.DetallesAdicionales().apply {
        with (detAdicional) {
            detalles.map {
                createDestinatarioDetalleAdicionale(it)
            }.forEach {
                add(it)
            }
        }
    }

private fun createDestinatarioDetalleAdicionale(
    detalle: DetalleAdicionale
) = ec.gob.sri.remision.v1_1_0.Detalle.DetallesAdicionales.DetAdicional().apply {
    nombre = detalle.nombre
    valor = detalle.valor
}
