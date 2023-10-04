package uk.co.xprl.efactura.jaxb.v2_0_0

import uk.co.xprl.efactura.Ambiente
import uk.co.xprl.efactura.TipoEmisión
import uk.co.xprl.efactura.ComprobanteRetencionATS as XprlComprobanteRetencionATS
import uk.co.xprl.efactura.adapters.*
import uk.co.xprl.efactura.jaxb.ClaveAcceso
import uk.co.xprl.efactura.jaxb.ambienteCodigo
import uk.co.xprl.efactura.jaxb.tipoEmisionCodigo

import ec.gob.sri.retencion.v2_0_0.ComprobanteRetencion as ComprobanteRetencion_2_0_0

private const val SI = "SI"
private const val NO = "NO"

/*
 * Differencias con V1.0.0:
> 	<xsd:complexType name="impuestoDocSustento">
> 		<xsd:sequence>
> 			<xsd:element name="codImpuestoDocSustento" minOccurs="1" type="codImpuestoDocSustento"/>
> 			<xsd:element name="codigoPorcentaje" minOccurs="1" type="codigoPorcentaje"/>
> 			<xsd:element name="baseImponible" minOccurs="1" type="baseImponible"/>
> 			<xsd:element name="tarifa" minOccurs="1" type="tarifa"/>
> 			<xsd:element name="valorImpuesto" minOccurs="1" type="valorImpuesto"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="dividendos">
> 		<xsd:sequence>
> 			<xsd:element name="fechaPagoDiv" minOccurs="1" type="fecha"/>
> 			<xsd:element name="imRentaSoc" minOccurs="1" type="imRentaSoc"/>
> 			<xsd:element name="ejerFisUtDiv" minOccurs="1" type="ejerFisUtDiv"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="compraCajBanano">
> 		<xsd:sequence>
> 			<xsd:element name="numCajBan" minOccurs="1" type="numCajBan"/>
> 			<xsd:element name="precCajBan" minOccurs="1" type="precCajBan"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="detalleImpuesto">
> 		<xsd:sequence>
> 			<xsd:element name="codigo" minOccurs="1" type="codigoReembolso"/>
> 			<xsd:element name="codigoPorcentaje" minOccurs="1" type="codigoPorcentajeReembolso"/>
> 			<xsd:element name="tarifa" minOccurs="1" type="tarifaReembolso"/>
> 			<xsd:element name="baseImponibleReembolso" minOccurs="1" type="baseImponibleReembolso"/>
> 			<xsd:element name="impuestoReembolso" minOccurs="1" type="impuestoReembolso"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="detalleImpuestos">
> 		<xsd:sequence maxOccurs="1">
> 			<xsd:element name="detalleImpuesto" type="detalleImpuesto" maxOccurs="unbounded"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="reembolsos">
> 		<xsd:sequence>
> 			<xsd:element name="reembolsoDetalle" minOccurs="1" maxOccurs="unbounded">
> 				<xsd:complexType>
> 					<xsd:sequence>
> 						<xsd:element name="tipoIdentificacionProveedorReembolso" minOccurs="1" type="tipoIdentificacionProveedorReembolso"/>
> 						<xsd:element name="identificacionProveedorReembolso" minOccurs="1" type="identificacionProveedorReembolso"/>
> 						<xsd:element name="codPaisPagoProveedorReembolso" minOccurs="0" type="codPaisPagoProveedorReembolso"/>
> 						<xsd:element name="tipoProveedorReembolso" minOccurs="1" type="tipoProveedorReembolso"/>
> 						<xsd:element name="codDocReembolso" minOccurs="1" type="codDocReembolso"/>
> 						<xsd:element name="estabDocReembolso" minOccurs="1" type="estabDocReembolso"/>
> 						<xsd:element name="ptoEmiDocReembolso" minOccurs="1" type="ptoEmiDocReembolso"/>
> 						<xsd:element name="secuencialDocReembolso" minOccurs="1" type="secuencialDocReembolso"/>
> 						<xsd:element name="fechaEmisionDocReembolso" minOccurs="1" type="fechaEmisionDocReembolso"/>
> 						<xsd:element name="numeroAutorizacionDocReemb" minOccurs="1" type="numeroautorizacionDocReemb"/>
> 						<xsd:element name="detalleImpuestos" minOccurs="1" type="detalleImpuestos"/>
> 					</xsd:sequence>
> 				</xsd:complexType>
> 			</xsd:element>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="impuestosDocSustento">
> 		<xsd:sequence maxOccurs="1">
> 			<xsd:element name="impuestoDocSustento" type="impuestoDocSustento" maxOccurs="unbounded"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="retencion">
187,189c229,284
< 			<xsd:element name="codDocSustento" minOccurs="0" type="codDocSustento"/>
< 			<xsd:element name="numDocSustento" minOccurs="0" type="numDocSustento"/>
< 			<xsd:element name="fechaEmisionDocSustento" type="fechaEmisionDocSustento" minOccurs="0"/>
---
> 			<xsd:element name="dividendos" minOccurs="0" type="dividendos"/>
> 			<xsd:element name="compraCajBanano" minOccurs="0" type="compraCajBanano"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="retenciones">
> 		<xsd:sequence maxOccurs="1">
> 			<xsd:element name="retencion" type="retencion" maxOccurs="unbounded"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="pago">
> 		<xsd:sequence>
> 			<xsd:element name="formaPago" minOccurs="1" type="formaPago"/>
> 			<xsd:element name="total" minOccurs="1" type="total"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="pagos">
> 		<xsd:sequence maxOccurs="1">
> 			<xsd:element name="pago" type="pago" maxOccurs="unbounded"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="docSustento">
> 		<xsd:annotation>
> 			<xsd:documentation>Informacion de los documentos que originan la retenciones.</xsd:documentation>
> 		</xsd:annotation>
> 		<xsd:sequence>
> 			<xsd:element name="codSustento" minOccurs="1" type="codSustento"/>
> 			<xsd:element name="codDocSustento" minOccurs="1" type="codDocSustento"/>
> 			<xsd:element name="numDocSustento" minOccurs="1" type="numDocSustento"/>
> 			<xsd:element name="fechaEmisionDocSustento" minOccurs="1" type="fecha"/>
> 			<xsd:element name="fechaRegistroContable" minOccurs="0" type="fecha"/>
> 			<xsd:element name="numAutDocSustento" minOccurs="0" type="numAutDocSustento"/>
> 			<xsd:element name="pagoLocExt" minOccurs="1" type="pagoLocExt"/>
> 			<xsd:element name="tipoRegi" minOccurs="0" type="tipoRegi"/>
> 			<xsd:element name="paisEfecPago" minOccurs="0" type="paisEfecPago"/>
> 			<xsd:element name="aplicConvDobTrib" minOccurs="0" type="afirmacion"/>
> 			<xsd:element name="pagExtSujRetNorLeg" minOccurs="0" type="afirmacion"/>
> 			<xsd:element name="pagoRegFis" minOccurs="0" type="afirmacion"/>
> 			<xsd:element name="totalComprobantesReembolso" minOccurs="0" type="totalComprobantesReembolso"/>
> 			<xsd:element name="totalBaseImponibleReembolso" minOccurs="0" type="totalBaseImponibleReembolso"/>
> 			<xsd:element name="totalImpuestoReembolso" minOccurs="0" type="totalImpuestoReembolso"/>
> 			<xsd:element name="totalSinImpuestos" minOccurs="1" type="totalSinImpuestos"/>
> 			<xsd:element name="importeTotal" minOccurs="1" type="importeTotal"/>
> 			<xsd:element name="impuestosDocSustento" type="impuestosDocSustento"/>
> 			<xsd:element name="retenciones" type="retenciones"/>
> 			<xsd:element name="reembolsos" minOccurs="0" type="reembolsos"/>
> 			<xsd:element name="pagos" type="pagos"/>
> 		</xsd:sequence>
> 	</xsd:complexType>
> 	<xsd:complexType name="maquinaFiscal">
> 		<xsd:annotation>
> 			<xsd:documentation>Contiene la informacion de las maquinas fiscales</xsd:documentation>
> 		</xsd:annotation>
> 		<xsd:sequence>
> 			<xsd:element name="marca"  minOccurs="1" type="cadenaTreinta"/>
> 			<xsd:element name="modelo"  minOccurs="1" type="cadenaTreinta"/>
> 			<xsd:element name="serie"  minOccurs="1" type="cadenaTreinta"/>
206a302,303
> 						    <xsd:element name="tipoSujetoRetenido" minOccurs="0" type="tipoSujetoRetenido"/>
> 							<xsd:element name="parteRel" minOccurs="1" type="parteRel"/>
213c310
< 				<xsd:element name="impuestos">
---
> 				<xsd:element name="docsSustento">
216c313
< 							<xsd:element name="impuesto" type="impuesto" maxOccurs="unbounded"/>
---
> 							<xsd:element name="docSustento" type="docSustento" maxOccurs="unbounded"/>
239c336
< 						<xsd:documentation xml:lang="es"> Conjunto de datos asociados a la factura que garantizarán la autoría y la integridad del mensaje. Se define como opcional para facilitar la verificación y el tránsito del fichero. No obstante, debe cumplimentarse este bloque de firma electrónica para que se considere una factura electrónica válida legalmente frente a terceros.</xsd:documentation>
---
> 						<xsd:documentation xml:lang="es"> Conjunto de datos asociados a la factura que garantizarÃ¡n la autorÃ­a y la integridad del mensaje. Se define como opcional para facilitar la verificaciÃ³n y el trÃ¡nsito del fichero. No obstante, debe cumplimentarse este bloque de firma electrÃ³nica para que se considere una factura electrÃ³nica vÃ¡lida legalmente frente a terceros.</xsd:documentation>
 */

internal fun createComprobanteRetencion(
    retencion: XprlComprobanteRetencionATS,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ComprobanteRetencion_2_0_0().apply {
    val formatted = ComprobanteRetencionATS(retencion)
    id = COMPROBANTE_ID
    version = "2.0.0"
    infoTributaria = createInfoTributaria(formatted, ambiente, tipoEmision, claveAcceso)
    infoCompRetencion = createInfoCompRetencion(formatted)
    docsSustento = createDocsSustento(formatted.valores.sustentos)
    infoAdicional = formatted.infoAdicional?.let {
        createInfosAdicionales(it)
    }
}

private fun createInfoTributaria(
    retencion: ComprobanteRetencionATS,
    ambiente: Ambiente,
    tipoEmision: TipoEmisión,
    claveAcceso: ClaveAcceso
) = ec.gob.sri.retencion.v2_0_0.InfoTributaria().apply {
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
    retencion: ComprobanteRetencionATS
) = ec.gob.sri.retencion.v2_0_0.ComprobanteRetencion.InfoCompRetencion().apply {
    fechaEmision = retencion.fechaEmision
    retencion.emisor.let {
        dirEstablecimiento = it.direcciónEstablecimiento
        contribuyenteEspecial = it.contribuyenteEspecial
        obligadoContabilidad = it.obligadoLlevarCompt?.let { isObligado ->
            if (isObligado)
                ec.gob.sri.retencion.v2_0_0.ObligadoContabilidad.SI
            else
                ec.gob.sri.retencion.v2_0_0.ObligadoContabilidad.NO
        }
    }
    retencion.sujeto.let {
        tipoIdentificacionSujetoRetenido = it.tipoIdentificación
        tipoSujetoRetenido = it.tipoSujetoRetenido
        parteRel = if (it.parteRelacionado) SI else NO
        identificacionSujetoRetenido = it.identificación
        razonSocialSujetoRetenido = it.razónSocial
    }
    retencion.valores.let {
        periodoFiscal = it.periodoFiscal
    }
}

private fun createDocsSustento(
    docsSustento: List<SustentoRetencion>
) = ec.gob.sri.retencion.v2_0_0.ComprobanteRetencion.DocsSustento().apply {
    with(this.docSustento) {
        docsSustento.map {
            createDocSustento(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createDocSustento(
    sustento: SustentoRetencion
) = ec.gob.sri.retencion.v2_0_0.DocSustento().apply {
    codSustento = sustento.codigo

    sustento.docSustento.let {
        codDocSustento = it.codDocSustento
        numDocSustento = it.numDocSustento
        fechaEmisionDocSustento = it.fechaEmisionDocSustento
        fechaRegistroContable = it.fechaRegistroContable
        numAutDocSustento = it.numAutDocSustento
    }

    sustento.pago.let {
        pagoLocExt = it.pagoLocExt
        tipoRegi = it.tipoRegi
        paisEfecPago = it.paisEfecPago
        aplicConvDobTrib = it.aplicConvDobTrib?.let { v -> if (v) SI else NO }
        pagExtSujRetNorLeg = it.pagExtSujRetNorLeg?.let { v -> if (v) SI else NO }
        pagoRegFis = it.pagRegFis?.let { v -> if (v) SI else NO }
    }

    totalComprobantesReembolso = sustento.totalComprobantesReembolso
    totalBaseImponibleReembolso = sustento.totalBaseImponibleReembolso
    totalImpuestoReembolso = sustento.totalImpuestoReembolso

    totalSinImpuestos = sustento.totalSinImpuestos
    importeTotal = sustento.importeTotal

    impuestosDocSustento = createImpuestosDocSustento(sustento.impuestos)

    retenciones = createRetenciones(sustento.retenciones)

    reembolsos = sustento.reembolsos?.let { createReembolsos(it) }

    pagos = createPagos(sustento.pagos)
}


private fun createImpuestosDocSustento(
    impuestos: List<ImpuestoDocSustento>
) = ec.gob.sri.retencion.v2_0_0.ImpuestosDocSustento().apply {
    with(this.impuestoDocSustento) {
        impuestos.map {
            createImpuestoDocSustento(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createImpuestoDocSustento(
    impuesto: ImpuestoDocSustento,
) = ec.gob.sri.retencion.v2_0_0.ImpuestoDocSustento().apply {
    codImpuestoDocSustento = impuesto.codImpuestoDocSustento
    codigoPorcentaje = impuesto.codigoPorcentaje
    baseImponible = impuesto.baseImponible
    tarifa = impuesto.tarifa
    valorImpuesto = impuesto.valor
}

private fun createRetenciones(
    retenciones: List<RetencionATS>
) = ec.gob.sri.retencion.v2_0_0.Retenciones().apply {
    with(this.retencion) {
        retenciones.map {
            createRetencion(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createRetencion(
    retencion: RetencionATS,
) = ec.gob.sri.retencion.v2_0_0.Retencion().apply {
    codigo = retencion.codigo
    codigoRetencion = retencion.codigoRetencion
    baseImponible = retencion.baseImponible
    porcentajeRetener = retencion.porcentajeRetener
    valorRetenido = retencion.valorRetenido

    dividendos = retencion.dividendos?.let { createDividendos(it) }
}

private fun createDividendos(
    dividendos: RetencionATS.DividendoDetalle
) = ec.gob.sri.retencion.v2_0_0.Dividendos().apply {
    fechaPagoDiv = dividendos.fechaPagoDiv
    imRentaSoc = dividendos.imRentaSoc
    ejerFisUtDiv = dividendos.ejerFisUtDiv
}

private fun createReembolsos(
    reembolsos: Iterable<ReembolsoDetalle>
) = ec.gob.sri.retencion.v2_0_0.Reembolsos().apply {
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
) = ec.gob.sri.retencion.v2_0_0.Reembolsos.ReembolsoDetalle().apply {
    tipoIdentificacionProveedorReembolso = reembolso.tipoIdentificación
    identificacionProveedorReembolso = reembolso.identificación
    codPaisPagoProveedorReembolso = reembolso.paisPago
    tipoProveedorReembolso = reembolso.tipoProveedorReembolso
    codDocReembolso = reembolso.docReembolso.codDocReembolso
    estabDocReembolso = reembolso.docReembolso.estabDocReembolso
    ptoEmiDocReembolso = reembolso.docReembolso.ptoEmiDocReembolso
    secuencialDocReembolso = reembolso.docReembolso.secuencialDocReembolso
    fechaEmisionDocReembolso = reembolso.docReembolso.fechaEmisionDocReembolso
    numeroAutorizacionDocReemb = reembolso.docReembolso.numeroAutorizacion
    detalleImpuestos = createReembolsoDetalleImpuestos(reembolso.impuestos)
}

private fun createReembolsoDetalleImpuestos(
    impuestos: Iterable<DetalleImpuesto>
) = ec.gob.sri.retencion.v2_0_0.DetalleImpuestos().apply {
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
) = ec.gob.sri.retencion.v2_0_0.DetalleImpuesto().apply {
    codigo = impuesto.codigo
    codigoPorcentaje = impuesto.codigoPorcentaje
    tarifa = impuesto.tarifa.toString()
    baseImponibleReembolso = impuesto.baseImponible
    impuestoReembolso = impuesto.valor
}


private fun createPagos(
    pagos: List<Pago>
) = ec.gob.sri.retencion.v2_0_0.Pagos().apply {
    with (pago) {
        pagos.map { it ->
            createPago(it)
        }.forEach {
            add(it)
        }
    }
}

private fun createPago(
    pago: Pago
) = ec.gob.sri.retencion.v2_0_0.Pago().apply {
    this.formaPago = pago.formaPago
    this.total = pago.total
}

private fun createInfosAdicionales(info: Map<String, String>) =
    ec.gob.sri.retencion.v2_0_0.ComprobanteRetencion.InfoAdicional().apply {
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
) = ec.gob.sri.retencion.v2_0_0.ComprobanteRetencion.InfoAdicional.CampoAdicional().apply {
    this.nombre = name
    this.value = value
}
