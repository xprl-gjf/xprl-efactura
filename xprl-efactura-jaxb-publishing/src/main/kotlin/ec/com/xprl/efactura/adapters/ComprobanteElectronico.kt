package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.ComprobanteElectronico
import ec.com.xprl.efactura.jaxb.sriDocumentType

internal sealed class ComprobanteElectronico(val src: ComprobanteElectronico) {
    val secuencial: String
        get() = String.format("%09d", src.secuencial.value)
    val fechaEmision: String
        get() = src.fechaEmision.toDateString()
    val codDoc: String
        get() = String.format("%02d", src.sriDocumentType.value)

    val emisor: Emisor = Emisor(src.emisor)
    val maquinaFiscal: MaquinaFiscal? = src.maquinaFiscal?.let { MaquinaFiscal(it) }

    val infoAdicional: Map<String, String>?
        get() = src.infoAdicional?.map { (k, v) ->
            k.value to v.value
        }?.toMap()
}