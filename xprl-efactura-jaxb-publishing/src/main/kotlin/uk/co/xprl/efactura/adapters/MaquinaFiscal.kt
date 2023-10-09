package uk.co.xprl.efactura.adapters

class MaquinaFiscal(val src: uk.co.xprl.efactura.MaquinaFiscal) {
    val marca: String
        get() = src.marca.value
    val modelo: String
        get() = src.modelo.value
    val serie: String
        get() = src.serie.value
}