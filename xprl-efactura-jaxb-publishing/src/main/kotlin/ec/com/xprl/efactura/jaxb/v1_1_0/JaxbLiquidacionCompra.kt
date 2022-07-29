package ec.com.xprl.efactura.jaxb.v1_1_0

import ec.com.xprl.efactura.Ambiente
import ec.com.xprl.efactura.TipoEmisión
import ec.com.xprl.efactura.LiquidacionCompra as XprlLiquidacionCompra
import ec.com.xprl.efactura.adapters.*
import ec.com.xprl.efactura.jaxb.ClaveAcceso
import ec.com.xprl.efactura.jaxb.ambienteCodigo
import ec.com.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra as LiquidacionCompra_1_1_0

/*
 * The only difference between schema v1.0.0 and v1.1.0 is the numerical precision
 * of the 'cantidad', 'precioUnitario' and 'precioSinSubsidio' fields:
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
      <xsd:simpleType name="precioUnitario">
          <xsd:restriction base="xsd:decimal">
 -            <xsd:totalDigits value="14"/>
 -            <xsd:fractionDigits value="2"/>
 +            <xsd:totalDigits value="18"/>
 +            <xsd:fractionDigits value="6"/>
              <xsd:minInclusive value="0"/>
          </xsd:restriction>
      </xsd:simpleType>
      <xsd:simpleType name="precioSinSubsidio">
          <xsd:restriction base="xsd:decimal">
 -            <xsd:totalDigits value="14"/>
 -            <xsd:fractionDigits value="2"/>
 +            <xsd:totalDigits value="18"/>
 +            <xsd:fractionDigits value="6"/>
              <xsd:minInclusive value="0"/>
          </xsd:restriction>
      </xsd:simpleType>
 */


internal fun createLiquidacionCompra(
    liquidacion: XprlLiquidacionCompra,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = LiquidacionCompra_1_1_0().apply {
    val formatted = LiquidacionCompra(liquidacion)
    id = COMPROBANTE_ID
    version = "1.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoLiquidacionCompra = createInfoLiquidacionCompra(formatted)
    detalles = createLiquidacionCompraDetalles(formatted)
}

private fun createInfoTributaria(
    liquidacion: LiquidacionCompra,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.liquidacion.v1_1_0.InfoTributaria().apply {
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
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.InfoLiquidacionCompra().apply {
    fechaEmision = liquidacion.fechaEmision
    liquidacion.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.liquidacion.v1_1_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.liquidacion.v1_1_0.ObligadoContabilidad.NO
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
}

private fun createLiquidacionCompraDetalles(
    liquidacion: LiquidacionCompra
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.Detalles().apply {
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
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.Detalles.Detalle().apply {
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
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.Detalles.Detalle.Impuestos().apply {
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
) = ec.gob.sri.liquidacion.v1_1_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
}

private fun createTotalConImpuestos(
    totalConImpuestos: Iterable<ImpuestoLiquidacion>
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos().apply {
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
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.InfoLiquidacionCompra.TotalConImpuestos.TotalImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    tarifa = impuesto.tarifa
}

private fun createPagos(pagos: List<Pago>) = ec.gob.sri.liquidacion.v1_1_0.Pagos().apply {
    with(pago) {
        pagos.map {
            createPago(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createPago(pago: Pago) = ec.gob.sri.liquidacion.v1_1_0.Pagos.Pago().apply {
    formaPago = pago.formaPago
    total = pago.total
    plazo = pago.plazoValue
    unidadTiempo = pago.plazoUnidadTiempo
}


private fun createDetallesAdicionales(detalles: Iterable<DetalleAdicionale>) =
    ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.Detalles.Detalle.DetallesAdicionales().apply {
        with (detAdicional) {
            detalles.map {
                createDetalleAdicionale(it)
            }.forEach {
                add(it)
            }
        }
    }

private fun createDetalleAdicionale(
    detalle: DetalleAdicionale
) = ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional().apply {
    nombre = detalle.nombre
    valor = detalle.valor
}
