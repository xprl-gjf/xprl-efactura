package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.DetalleAdicionale

class DetalleAdicionale(val src: DetalleAdicionale) {
    val nombre: String
        get() = src.nombre.value
    val valor: String
        get() = src.valor.value
}