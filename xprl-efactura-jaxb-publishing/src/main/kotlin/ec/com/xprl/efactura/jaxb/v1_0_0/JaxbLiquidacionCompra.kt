package ec.com.xprl.efactura.jaxb.v1_0_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.LiquidacionCompra as XprlLiquidacionCompra
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra as LiquidacionCompra_1_0_0


internal fun createLiquidacionCompra(
    liquidacion: XprlLiquidacionCompra,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = LiquidacionCompra_1_0_0().apply {
    val formatted = LiquidacionCompra(liquidacion)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoLiquidacionCompra = createInfoLiquidacionCompra(formatted)
    detalles = createLiquidacionCompraDetalles(formatted)

    formatted.reembolsoDetalles?.let {
        reembolsos = createLiquidacionCompraReembolsos(it)
    }
    maquinaFiscal = formatted.maquinaFiscal?.let {
        createMaquinaFiscal(it)
    }
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}

private fun createInfoTributaria(
    liquidacion: LiquidacionCompra,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.liquidacion.v1_0_0.InfoTributaria().apply {
    this.ambiente = ambiente.ambienteCodigo.toString()
    this.tipoEmision = tipoEmision.tipoEmisionCodigo.toString()
    this.claveAcceso = claveAcceso.toString()
    this.secuencial = liquidacion.secuencial
    this.codDoc = liquidacion.codDoc

    liquidacion.emisor.let {
        razonSocial = it.razónSocial
        ruc = it.RUC
        estab = it.códigoEstablecimiento
        ptoEmi = it.códigoPuntoEmisión
        dirMatriz = it.direccionMatriz
        nombreComercial = it.nombreComercial
    }
}

private fun createInfoLiquidacionCompra(
    liquidacion: LiquidacionCompra
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.InfoLiquidacionCompra().apply {
    fechaEmision = liquidacion.fechaEmision
    liquidacion.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.liquidacion.v1_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.liquidacion.v1_0_0.ObligadoContabilidad.NO
        }
    }
    liquidacion.proveedor.let {
        tipoIdentificacionProveedor = it.tipoIdentificación
        identificacionProveedor = it.identificación
        razonSocialProveedor = it.razónSocial
        direccionProveedor = it.dirección
    }
    liquidacion.valores.totals.let {
        totalSinImpuestos = it.totalSinImpuestos
        totalDescuento = it.totalDescuento
        importeTotal = it.importeTotal
        totalConImpuestos = createTotalConImpuestos(it.totalConImpuestos)
    }
    liquidacion.valores.let {
        moneda = it.moneda
        pagos = createPagos(it.pagos)
    }
    liquidacion.reembolso?.let {
        codDocReembolso = it.codDocReembolso
        totalComprobantesReembolso = it.totals.totalComprobantesReembolso
        totalBaseImponibleReembolso = it.totals.totalBaseImponibleReembolso
        totalImpuestoReembolso = it.totals.totalImpuestoReembolso
    }
}


private fun createLiquidacionCompraReembolsos(
    reembolsos: Iterable<ReembolsoDetalle>
) = ec.gob.sri.liquidacion.v1_0_0.Reembolsos().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.Reembolsos.ReembolsoDetalle().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.DetalleImpuestos().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.DetalleImpuestos.DetalleImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponibleReembolso = impuesto.baseImponible
    impuestoReembolso = impuesto.valor
}

private fun createLiquidacionCompraDetalles(
    liquidacion: LiquidacionCompra
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.Detalles().apply {
    with(detalle) {
        liquidacion.detalles.map {
            createLiquidacionDetalle(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createLiquidacionDetalle(
    detalle: ComprobanteDetalle
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.Detalles.Detalle().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.Detalles.Detalle.Impuestos().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
}

private fun createTotalConImpuestos(
    totalConImpuestos: Iterable<ImpuestoLiquidacion>
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos().apply {
    with(totalImpuesto) {
        totalConImpuestos.map {
            createTotalImpuesto(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createTotalImpuesto(
    impuesto: ImpuestoLiquidacion
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    tarifa = impuesto.tarifa
}

private fun createPagos(pagos: List<Pago>) = ec.gob.sri.liquidacion.v1_0_0.Pagos().apply {
    with(pago) {
        pagos.map {
            createPago(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createPago(pago: Pago) = ec.gob.sri.liquidacion.v1_0_0.Pagos.Pago().apply {
    formaPago = pago.formaPago
    total = pago.total
    plazo = pago.plazoValue
    unidadTiempo = pago.plazoUnidadTiempo
}

private fun createDetallesAdicionales(detalles: Map<String, String>) =
    ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.Detalles.Detalle.DetallesAdicionales().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional().apply {
    this.nombre = nombre
    this.valor = valor
}

private fun createMaquinaFiscal(
    maquinaFiscal: MaquinaFiscal
) = ec.gob.sri.liquidacion.v1_0_0.MaquinaFiscal().apply {
    marca = maquinaFiscal.marca
    modelo = maquinaFiscal.modelo
    serie = maquinaFiscal.serie
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.InfoAdicional().apply {
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
) = ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
