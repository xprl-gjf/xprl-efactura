package ec.com.xprl.efactura

/**
 * Valores por el campo `código` por impuestos.
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 16.
 */
enum class TipoImpuesto(val value: Int) {
    IVA(2),
    ICE(3),
    IRBPNR(5);

    companion object {
        /* empty - included solely to support extensions */
    }
}

/**
 * Unique identification for an impuesto
 */
data class ImpuestoIdentidad(
    val tipoImpuesto: TipoImpuesto,
    val codigoPorcentaje: Int
) {
    companion object {
        /* empty - included solely to support extensions */
    }
}

/**
 * Immutable representation of an impuesto total value for a comprobante electrónico.
 */
data class ImpuestoTotal(
    val baseImponible: UDecimalValue,
    val valor: UDecimalValue,
    val descuentoAdicional: UDecimalValue? = null,
    val valorDevolucionIva: UDecimalValue? = null
)

/**
 * Immutable representation of an impuesto total value for a liquidación de compra.
 */
data class ImpuestoLiquidacionTotal(
    val baseImponible: UDecimalValue,
    val valor: UDecimalValue,
    val descuentoAdicional: UDecimalValue? = null,
    val tarifa: UDecimalValue? = null
)

/**
 * Immutable representation of an impuesto detalle value for a comprobante electrónico.
 */
data class ImpuestoDetalle(
    val tarifa: UDecimalValue,
    val baseImponible: UDecimalValue,
    val valor: UDecimalValue
)