package ec.com.xprl.efactura.jaxb.v1_0_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.ComprobanteRetencion as XprlComprobanteRetencion
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion as ComprobanteRetencion_1_0_0


internal fun createComprobanteRetencion(
    retencion: XprlComprobanteRetencion,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ComprobanteRetencion_1_0_0().apply {
    val formatted = ComprobanteRetencion(retencion)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoCompRetencion = createInfoCompRetencion(formatted)
    impuestos = createImpuestos(formatted.valores.impuestos)
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}

private fun createInfoTributaria(
    retencion: ComprobanteRetencion,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.retencion.v1_0_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = retencion.secuencial
    this.codDoc = retencion.codDoc

    retencion.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoCompRetencion(
    retencion: ComprobanteRetencion
) = ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion.InfoCompRetencion().apply {
    fechaEmision = retencion.fechaEmision
    retencion.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.retencion.v1_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.retencion.v1_0_0.ObligadoContabilidad.NO
        }
    }
    retencion.sujecto.let {
        tipoIdentificacionSujetoRetenido = it.tipoIdentificación
        identificacionSujetoRetenido = it.identificación
        razonSocialSujetoRetenido = it.razónSocial
    }
    retencion.valores.let {
        periodoFiscal = it.periodoFiscal
    }
}

private fun createImpuestos(
    impuestos: List<ImpuestoRetencion>
) = ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion.Impuestos().apply {
    with(this.impuesto) {
        impuestos.map {
            createImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createImpuesto(
    impuesto: ImpuestoRetencion,
) = ec.gob.sri.retencion.v1_0_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoRetencion = impuesto.codigoRetencion
    baseImponible = impuesto.baseImponible
    porcentajeRetener = impuesto.porcentajeRetener
    valorRetenido = impuesto.valorRetenido

    codDocSustento = impuesto.codDocSustento
    numDocSustento = impuesto.numDocSustento
    fechaEmisionDocSustento = impuesto.fechaEmisionDocSustento
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion.InfoAdicional().apply {
        with (campoAdicional) {
            info.map { (name, value) ->
                createInfoAdicionale(name, value)
            }.forEach {
                add(it)
            }
        }
    }

private fun createInfoAdicionale(
    name: String,
    value: String
) = ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
