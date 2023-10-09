package uk.co.xprl.efactura.jaxb.v1_0_0

import uk.co.xprl.efactura.Ambiente
import uk.co.xprl.efactura.TipoEmisión
import uk.co.xprl.efactura.Factura as XprlFactura
import uk.co.xprl.efactura.adapters.*
import uk.co.xprl.efactura.jaxb.ClaveAcceso
import uk.co.xprl.efactura.jaxb.ambienteCodigo
import uk.co.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.factura.v1_0_0.Factura as Factura_1_0_0


internal fun createFactura(
    factura: XprlFactura,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = Factura_1_0_0().apply {
    val formatted = Factura(factura)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoFactura = createInfoFactura(formatted)
    detalles = createFacturaDetalles(formatted)
    reembolsos = formatted.reembolsoDetalles?.let {
        createReembolsos(it)
    }
    tipoNegociable = formatted.tipoNegociable?.let {
        createTipoNegociable(it)
    }
    maquinaFiscal = formatted.maquinaFiscal?.let {
        createMaquinaFiscal(it)
    }
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}

private fun createInfoTributaria(
    factura: Factura,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.factura.v1_0_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = factura.secuencial
    this.codDoc = factura.codDoc

    factura.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoFactura(
    factura: Factura
) = ec.gob.sri.factura.v1_0_0.Factura.InfoFactura().apply {
    fechaEmision = factura.fechaEmision
    factura.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.factura.v1_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.factura.v1_0_0.ObligadoContabilidad.NO
        }
    }
    factura.comprador.let {
        tipoIdentificacionComprador = it.tipoIdentificación
        identificacionComprador = it.identificación
        razonSocialComprador = it.razónSocial
        direccionComprador = it.dirección
    }
    factura.valores.totals.let {
        totalSinImpuestos = it.totalSinImpuestos
        totalDescuento = it.totalDescuento
        importeTotal = it.importeTotal
        totalConImpuestos = createTotalConImpuestos(it.totalConImpuestos)
    }
    factura.valores.let {
        propina = it.propina
        moneda = it.moneda
        pagos = createPagos(it.pagos)
        valorRetIva = it.retIva
        valorRetRenta = it.retRenta
    }
    factura.reembolso?.let {
        codDocReembolso = it.codDocReembolso
        totalComprobantesReembolso = it.totals.totalComprobantesReembolso
        totalBaseImponibleReembolso = it.totals.totalBaseImponibleReembolso
        totalImpuestoReembolso = it.totals.totalImpuestoReembolso
    }
}

private fun createFacturaDetalles(
    factura: Factura
) = ec.gob.sri.factura.v1_0_0.Factura.Detalles().apply {
    with(detalle) {
        factura.detalles.map {
            createComprobanteDetalle(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createComprobanteDetalle(
    detalle: ComprobanteDetalle
) = ec.gob.sri.factura.v1_0_0.Factura.Detalles.Detalle().apply {
    codigoPrincipal = detalle.codigoPrincipal
    descripcion = detalle.descripcion
    cantidad = detalle.cantidad
    precioUnitario = detalle.precioUnitario
    descuento = detalle.descuento
    precioTotalSinImpuesto = detalle.precioTotalSinImpuesto
    codigoAuxiliar = detalle.codigoAuxiliar

    impuestos = createImpuestos(detalle.impuestos)
    detalle.detallesAdicionales?.let {
        detallesAdicionales = createDetallesAdicionales(it)
    }
}

private fun createImpuestos(
    impuestos: Iterable<DetalleImpuesto>
) = ec.gob.sri.factura.v1_0_0.Factura.Detalles.Detalle.Impuestos().apply {
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
) = ec.gob.sri.factura.v1_0_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
}

private fun createTotalConImpuestos(
    totalConImpuestos: Iterable<Impuesto>
) = ec.gob.sri.factura.v1_0_0.Factura.InfoFactura.TotalConImpuestos().apply {
    with(totalImpuesto) {
        totalConImpuestos.map {
            createTotalImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createTotalImpuesto(
    impuesto: Impuesto
) = ec.gob.sri.factura.v1_0_0.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    descuentoAdicional = impuesto.descuentoAdicional
    valorDevolucionIva = impuesto.valorDevolucionIva
}

private fun createPagos(pagos: List<Pago>) = ec.gob.sri.factura.v1_0_0.Pagos().apply {
    with(pago) {
        pagos.map {
            createPago(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createPago(pago: Pago) = ec.gob.sri.factura.v1_0_0.Pagos.Pago().apply {
    formaPago = pago.formaPago
    total = pago.total
    plazo = pago.plazoValue
    unidadTiempo = pago.plazoUnidadTiempo
}

private fun createDetallesAdicionales(detalles: Map<String, String>) =
    ec.gob.sri.factura.v1_0_0.Factura.Detalles.Detalle.DetallesAdicionales().apply {
        with (detAdicional) {
            detalles.map { (nombre, valor) ->
                createDetalleAdicionale(nombre, valor)
            }.forEach {
                add(it)
            }
        }
    }

private fun createDetalleAdicionale(
    nombre: String,
    valor: String
) = ec.gob.sri.factura.v1_0_0.Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional().apply {
    this.nombre = nombre
    this.valor = valor
}

private fun createReembolsos(
    reembolsos: Iterable<ReembolsoDetalle>
) = ec.gob.sri.factura.v1_0_0.Reembolsos().apply {
    with (reembolsoDetalle) {
        reembolsos.map {
            createReembolsoDetalle(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createReembolsoDetalle(
    reembolso: ReembolsoDetalle
) = ec.gob.sri.factura.v1_0_0.Reembolsos.ReembolsoDetalle().apply {
    tipoIdentificacionProveedorReembolso = reembolso.tipoIdentificación
    identificacionProveedorReembolso = reembolso.identificación
    codPaisPagoProveedorReembolso = reembolso.paisPago
    tipoProveedorReembolso = reembolso.tipoProveedorReembolso
    codDocReembolso = reembolso.docReembolso.codDocReembolso
    estabDocReembolso = reembolso.docReembolso.estabDocReembolso
    ptoEmiDocReembolso = reembolso.docReembolso.ptoEmiDocReembolso
    secuencialDocReembolso = reembolso.docReembolso.secuencialDocReembolso
    fechaEmisionDocReembolso = reembolso.docReembolso.fechaEmisionDocReembolso
    numeroautorizacionDocReemb = reembolso.docReembolso.numeroAutorizacion
    detalleImpuestos = createReembolsoDetalleImpuestos(reembolso.impuestos)
}

private fun createReembolsoDetalleImpuestos(
    impuestos: Iterable<DetalleImpuesto>
) = ec.gob.sri.factura.v1_0_0.DetalleImpuestos().apply {
    with (detalleImpuesto) {
        impuestos.map {
            createReembolsoDetalleImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createReembolsoDetalleImpuesto(
    impuesto: DetalleImpuesto
) = ec.gob.sri.factura.v1_0_0.DetalleImpuestos.DetalleImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponibleReembolso = impuesto.baseImponible
    impuestoReembolso = impuesto.valor
}

private fun createTipoNegociable(
    tipoNegociable: Factura.TipoNegociable
) = ec.gob.sri.factura.v1_0_0.TipoNegociable().apply {
    correo = tipoNegociable.correo
}

private fun createMaquinaFiscal(
    maquinaFiscal: MaquinaFiscal
) = ec.gob.sri.factura.v1_0_0.MaquinaFiscal().apply {
    marca = maquinaFiscal.marca
    modelo = maquinaFiscal.modelo
    serie = maquinaFiscal.serie
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.factura.v1_0_0.Factura.InfoAdicional().apply {
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
) = ec.gob.sri.factura.v1_0_0.Factura.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
