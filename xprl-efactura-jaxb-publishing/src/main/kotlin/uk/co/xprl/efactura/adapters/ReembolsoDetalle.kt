package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.ReembolsoDetalle

class ReembolsoDetalle(val src: ReembolsoDetalle) {
    val tipoIdentificación: String
        get() = String.format("%02d", src.proveedor.tipoIdentificacionCodigo)
    val identificación: String
        get() = src.proveedor.value
    val paisPago: String
        get() = String.format("%03d", src.paisPago.value)
    val tipoProveedorReembolso: String
        get() = String.format("%02d", src.tipoProveedorReembolso.value)

    val docReembolso = DocReembolso(src.docReembolso)
    val impuestos: List<DetalleImpuesto> = src.impuestos.map { (k, v) ->
        DetalleImpuesto(k, v)
    }


    class DocReembolso(val src: uk.co.xprl.efactura.DocReembolso) {
        val codDocReembolso: String
            get() = String.format("%02d", src.códigoDocumento.value)
        val estabDocReembolso: String
            get() = String.format("%03d", src.códigoEstablecimiento.value)
        val ptoEmiDocReembolso: String
            get() = String.format("%03d", src.códigoPuntoEmisión.value)
        val secuencialDocReembolso: String
            get() = String.format("%09d", src.secuencial.value)
        val fechaEmisionDocReembolso: String
            get() = src.fechaEmision.toDateString()
        val numeroAutorizacion: String
            get() = src.numeroAutorizacion.value
    }
}