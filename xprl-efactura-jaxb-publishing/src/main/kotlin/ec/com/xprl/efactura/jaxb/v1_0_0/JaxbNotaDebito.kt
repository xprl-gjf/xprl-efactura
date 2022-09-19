package ec.com.xprl.efactura.jaxb.v1_0_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.NotaDebito as XprlNotaDebito
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.debito.v1_0_0.NotaDebito as NotaDebito_1_0_0


internal fun createNotaDebito(
    notaDebito: XprlNotaDebito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = NotaDebito_1_0_0().apply {
    val formatted = NotaDebito(notaDebito)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoNotaDebito = createInfoNotaDebito(formatted)
    motivos = createMotivos(formatted)
    maquinaFiscal = formatted.maquinaFiscal?.let {
        createMaquinaFiscal(it)
    }
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}


private fun createInfoTributaria(
    debito: NotaDebito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.debito.v1_0_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = debito.secuencial
    this.codDoc = debito.codDoc

    debito.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoNotaDebito(
    debito: NotaDebito
) = ec.gob.sri.debito.v1_0_0.NotaDebito.InfoNotaDebito().apply {
    fechaEmision = debito.fechaEmision
    debito.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.debito.v1_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.debito.v1_0_0.ObligadoContabilidad.NO
        }
    }
    debito.comprador.let {
        tipoIdentificacionComprador = it.tipoIdentificación
        identificacionComprador = it.identificación
        razonSocialComprador = it.razónSocial
    }
    debito.debito.let {
        codDocModificado = it.codDocModificado
        numDocModificado = it.numDocModificado
        fechaEmisionDocSustento = it.fechaEmisionDocSustento
        rise = it.rise
    }
    debito.debito.valores.let {
        totalSinImpuestos = it.totalSinImpuestos
        valorTotal = it.valorTotal
        impuestos = createImpuestos(it.impuestos)
    }
}

private fun createImpuestos(
    impuestos: Iterable<Impuesto>
) = ec.gob.sri.debito.v1_0_0.NotaDebito.InfoNotaDebito.Impuestos().apply {
    with(this.impuesto) {
        impuestos.map {
            createImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createImpuesto(
    impuesto: Impuesto,
) = ec.gob.sri.debito.v1_0_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    valorDevolucionIva = impuesto.valorDevolucionIva
}

private fun createMotivos(
    notaDebito: NotaDebito
) = ec.gob.sri.debito.v1_0_0.NotaDebito.Motivos().apply {
    with(this.motivo) {
        notaDebito.debito.motivos.map {
            createMotivo(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createMotivo(
    motivo: NotaDebito.Motivo
) = ec.gob.sri.debito.v1_0_0.NotaDebito.Motivos.Motivo().apply {
    razon = motivo.razon
    valor = motivo.valor
}


private fun createMaquinaFiscal(
    maquinaFiscal: MaquinaFiscal
) = ec.gob.sri.debito.v1_0_0.MaquinaFiscal().apply {
    marca = maquinaFiscal.marca
    modelo = maquinaFiscal.modelo
    serie = maquinaFiscal.serie
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.debito.v1_0_0.NotaDebito.InfoAdicional().apply {
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
) = ec.gob.sri.debito.v1_0_0.NotaDebito.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
