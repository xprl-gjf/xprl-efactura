package ec.com.xprl.efactura.adapters

class MaquinaFiscal(val src: ec.com.xprl.efactura.MaquinaFiscal) {
    val marca: String
        get() = src.marca.value
    val modelo: String
        get() = src.modelo.value
    val serie: String
        get() = src.serie.value
}