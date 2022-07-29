package ec.com.xprl.efactura.jaxb.v1_0_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.NotaCredito as XprlNotaCredito
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.credito.v1_0_0.NotaCredito as NotaCredito_1_0_0


internal fun createNotaCredito(
    notaCredito: XprlNotaCredito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = NotaCredito_1_0_0().apply {
    val formatted = NotaCredito(notaCredito)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoNotaCredito = createInfoNotaCredito(formatted)
    detalles = createComprobanteDetalles(formatted)
}

private fun createInfoTributaria(
    credito: NotaCredito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.credito.v1_0_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = credito.secuencial
    this.codDoc = credito.codDoc

    credito.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoNotaCredito(
    credito: NotaCredito
) = ec.gob.sri.credito.v1_0_0.NotaCredito.InfoNotaCredito().apply {
    fechaEmision = credito.fechaEmision
    credito.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.credito.v1_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.credito.v1_0_0.ObligadoContabilidad.NO
        }
    }
    credito.comprador.let {
        tipoIdentificacionComprador = it.tipoIdentificación
        identificacionComprador = it.identificación
        razonSocialComprador = it.razónSocial
    }
    credito.credito.let {
        motivo = it.motivo
        codDocModificado = it.codDocModificado
        numDocModificado = it.numDocModificado
        fechaEmisionDocSustento = it.fechaEmisionDocSustento
        rise = it.rise
    }
    credito.credito.valores.let {
        totalSinImpuestos = it.totalSinImpuestos
        valorModificacion = it.valorModificacion
        totalConImpuestos = createTotalConImpuestos(it.totalConImpuestos)
        moneda = it.moneda
    }
}

private fun createComprobanteDetalles(
    credito: NotaCredito
) = ec.gob.sri.credito.v1_0_0.NotaCredito.Detalles().apply {
    with(detalle) {
        credito.detalles.map {
            createComprobanteDetalle(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createComprobanteDetalle(
    detalle: ComprobanteDetalle
) = ec.gob.sri.credito.v1_0_0.NotaCredito.Detalles.Detalle().apply {
    codigoInterno = detalle.codigoPrincipal
    descripcion = detalle.descripcion
    cantidad = detalle.cantidad
    precioUnitario = detalle.precioUnitario
    descuento = detalle.descuento
    precioTotalSinImpuesto = detalle.precioTotalSinImpuesto
    codigoAdicional = detalle.codigoAuxiliar

    impuestos = createImpuestos(detalle.impuestos)
}

private fun createImpuestos(impuestos: Iterable<DetalleImpuesto>) =
    ec.gob.sri.credito.v1_0_0.NotaCredito.Detalles.Detalle.Impuestos().apply {
    with (impuesto) {
        impuestos.map {
            createImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createImpuesto(
    impuesto: DetalleImpuesto
) = ec.gob.sri.credito.v1_0_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
}

private fun createTotalConImpuestos(
    totalConImpuestos: Iterable<Impuesto>
) = ec.gob.sri.credito.v1_0_0.TotalConImpuestos().apply {
    with(totalImpuesto) {
        totalConImpuestos.map {
            createTotalImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createTotalImpuesto(
    impuesto: Impuesto,
) = ec.gob.sri.credito.v1_0_0.TotalConImpuestos.TotalImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    valorDevolucionIva = impuesto.valorDevolucionIva
}
