package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.ReembolsoDetalle

internal class ReembolsoDetalle(val src: ReembolsoDetalle) {
    val tipoIdentificación: String
        get() = String.format("%02d", src.proveedor.tipoIdentificacionCodigo)
    val identificación: String
        get() = src.proveedor.value
    val paisPago: String?
        get() = src.paisPago?.let { String.format("%03d", it.value) }
    val tipoProveedorReembolso: String
        get() = String.format("%02d", src.tipoProveedorReembolso.value)

    val docReembolso = DocReembolso(src.docReembolso)
    val impuestos: List<DetalleImpuesto> = src.impuestos.map { (k, v) ->
        DetalleImpuesto(k, v)
    }


    internal class DocReembolso(val src: ec.com.xprl.efactura.DocReembolso) {
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