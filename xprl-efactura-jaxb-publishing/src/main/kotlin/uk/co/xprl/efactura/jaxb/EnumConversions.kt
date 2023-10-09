package uk.co.xprl.efactura.jaxb

import uk.co.xprl.efactura.*
import ec.gob.sri.efactura.TipoEmision

val Ambiente.ambienteCodigo: Int
    get() = when(this) {
        Ambiente.PRUEBAS -> ec.gob.sri.efactura.Ambiente.PRUEBAS.value
        Ambiente.PRODUCCIÓN -> ec.gob.sri.efactura.Ambiente.PRODUCCION.value
    }

val TipoEmisión.tipoEmisionCodigo: Int
    get() = when(this) {
        TipoEmisión.NORMAL -> TipoEmision.NORMAL.value
    }


internal val ComprobanteElectronico.sriDocumentType: ec.gob.sri.efactura.DocumentType
    get() = when(this) {
        is Factura -> ec.gob.sri.efactura.DocumentType.FACTURA
        is NotaCredito -> ec.gob.sri.efactura.DocumentType.NOTA_DE_CREDITO
        is NotaDebito -> ec.gob.sri.efactura.DocumentType.NOTA_DE_DEBITO
        is ComprobanteRetencion -> ec.gob.sri.efactura.DocumentType.COMPROBANTE_DE_RETENCION
        is GuiaRemision -> ec.gob.sri.efactura.DocumentType.GUIA_DE_REMISION
        is LiquidacionCompra -> ec.gob.sri.efactura.DocumentType.LIQUIDACION_DE_COMPRA
    }
