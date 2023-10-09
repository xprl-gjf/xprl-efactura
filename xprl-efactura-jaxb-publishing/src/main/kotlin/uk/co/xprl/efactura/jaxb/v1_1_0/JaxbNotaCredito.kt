package uk.co.xprl.efactura.jaxb.v1_1_0

import uk.co.xprl.efactura.Ambiente
import uk.co.xprl.efactura.TipoEmisión
import uk.co.xprl.efactura.NotaCredito as XprlNotaCredito
import uk.co.xprl.efactura.adapters.*
import uk.co.xprl.efactura.jaxb.ClaveAcceso
import uk.co.xprl.efactura.jaxb.ambienteCodigo
import uk.co.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.credito.v1_1_0.NotaCredito as NotaCredito_1_1_0

/*
 * The only difference between schema v1.0.0 and v1.1.0 is the numerical precision
 * of the 'cantidad' and 'precioUnitario fields:

        <xsd:simpleType name="cantidad">
                <xsd:restriction base="xsd:decimal">
-                       <xsd:totalDigits value="14"/>
-                       <xsd:fractionDigits value="2"/>
+                       <xsd:totalDigits value="18"/>
+                       <xsd:fractionDigits value="6"/>
                        <xsd:minInclusive value="0"/>
                </xsd:restriction>
        </xsd:simpleType>
        <xsd:simpleType name="precioUnitario">
                <xsd:restriction base="xsd:decimal">
-                       <xsd:totalDigits value="14"/>
-                       <xsd:fractionDigits value="2"/>
+                       <xsd:totalDigits value="18"/>
+                       <xsd:fractionDigits value="6"/>
                        <xsd:minInclusive value="0"/>
                </xsd:restriction>
        </xsd:simpleType>
 */

internal fun createNotaCredito(
    notaCredito: XprlNotaCredito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = NotaCredito_1_1_0().apply {
    val formatted = NotaCredito(notaCredito)
    id = COMPROBANTE_ID
    version = "1.1.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoNotaCredito = createInfoNotaCredito(formatted)
    detalles = createComprobanteDetalles(formatted)
    maquinaFiscal = formatted.maquinaFiscal?.let {
        createMaquinaFiscal(it)
    }
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}

private fun createInfoTributaria(
    credito: NotaCredito,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.credito.v1_1_0.InfoTributaria().apply {
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
) = ec.gob.sri.credito.v1_1_0.NotaCredito.InfoNotaCredito().apply {
    fechaEmision = credito.fechaEmision
    credito.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.credito.v1_1_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.credito.v1_1_0.ObligadoContabilidad.NO
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
) = ec.gob.sri.credito.v1_1_0.NotaCredito.Detalles().apply {
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
) = ec.gob.sri.credito.v1_1_0.NotaCredito.Detalles.Detalle().apply {
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
    ec.gob.sri.credito.v1_1_0.NotaCredito.Detalles.Detalle.Impuestos().apply {
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
) = ec.gob.sri.credito.v1_1_0.Impuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
}

private fun createTotalConImpuestos(
    totalConImpuestos: Iterable<Impuesto>
) = ec.gob.sri.credito.v1_1_0.TotalConImpuestos().apply {
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
) = ec.gob.sri.credito.v1_1_0.TotalConImpuestos.TotalImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    valor = impuesto.valor
    valorDevolucionIva = impuesto.valorDevolucionIva
}

private fun createMaquinaFiscal(
    maquinaFiscal: MaquinaFiscal
) = ec.gob.sri.credito.v1_1_0.MaquinaFiscal().apply {
    marca = maquinaFiscal.marca
    modelo = maquinaFiscal.modelo
    serie = maquinaFiscal.serie
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.credito.v1_1_0.NotaCredito.InfoAdicional().apply {
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
) = ec.gob.sri.credito.v1_1_0.NotaCredito.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
