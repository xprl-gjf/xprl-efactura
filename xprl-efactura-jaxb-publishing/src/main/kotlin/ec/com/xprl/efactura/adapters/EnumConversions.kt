package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.Pago
import ec.gob.sri.efactura.IdentificationType

val IdentityValue.tipoIdentificacionCodigo: Int
    get() = when(this) {
        is IdentityValue.RUC -> IdentificationType.RUC.value
        is IdentityValue.Cedula -> IdentificationType.CEDULA.value
        is IdentityValue.Pasaporte -> IdentificationType.PASAPORTE.value
        is IdentityValue.ConsumidorFinal -> IdentificationType.CONSUMIDOR_FINAL.value
        is IdentityValue.IdentificacionDelExterior -> IdentificationType.EXTERIOR.value
        // else -> throw Exception("Unhandled IdentityValue type: ${this::class.simpleName}.")
    }

val FormaDePago.formaDePagoCodigo: Int
    get() = when(this) {
        FormaDePago.SIN_SISTEMA_FINANCIERO -> ec.gob.sri.efactura.FormaDePago.SIN_SISTEMA_FINANCIERO.value
        FormaDePago.COMPENSACION_DE_DEUDAS -> ec.gob.sri.efactura.FormaDePago.COMPENSACION_DE_DEUDAS.value
        FormaDePago.DINERO_ELECTRONICO -> ec.gob.sri.efactura.FormaDePago.DINERO_ELECTRONICO.value
        FormaDePago.ENDOSO_DE_TITULOS -> ec.gob.sri.efactura.FormaDePago.ENDOSO_DE_TITULOS.value
        FormaDePago.OTROS_CON_SISTEMA_FINANCIERO -> ec.gob.sri.efactura.FormaDePago.OTROS_CON_SISTEMA_FINANCIERO.value
        FormaDePago.TARJETA_DE_CREDITO -> ec.gob.sri.efactura.FormaDePago.TARJETA_DE_CREDITO.value
        FormaDePago.TARJETA_DE_DEBITO -> ec.gob.sri.efactura.FormaDePago.TARJETA_DE_DEBITO.value
        FormaDePago.TARJETA_PREPAGO -> ec.gob.sri.efactura.FormaDePago.TARJETA_PREPAGO.value
    }

val TipoImpuesto.tipoImpuestoCodigo: Int
    get() = when(this) {
        TipoImpuesto.ICE -> ec.gob.sri.efactura.TipoImpuesto.ICE.value
        TipoImpuesto.IVA -> ec.gob.sri.efactura.TipoImpuesto.IVA.value
        TipoImpuesto.IRBPNR -> ec.gob.sri.efactura.TipoImpuesto.IRBPNR.value
    }

val Pago.Plazo.UnidadTiempo.jaxbString: String
    get() = this.name.lowercase()
