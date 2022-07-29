@file:Suppress("unused")

package ec.com.xprl.efactura


sealed class ImpuestoRetencionIdentidad(
    val tipoImpuesto: TipoImpuestoRetencion,
    val codigoPorcentaje: Int)


data class ImpuestoRetencionValor(
    val baseImponible: UDecimalValue,
    val porcentajeRetener: UDecimalValue,
    val valorRetenido: UDecimalValue
)

/**
 * Valores por el campo `código` por impuestos a retener.
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 19.
 */
enum class TipoImpuestoRetencion(val codigo: Int) {
    RENTA(1),
    IVA(2),
    ISD(6)
}

class ImpuestoRetencionIva(
    porcentaje: IvaRetencionPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.IVA, porcentaje.codigo) {

    /**
     * Valores por el campo `códigoPorcentaje` por impuestos a retener IVA.
     *
     * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
     * Tabla 20.
     */
    enum class IvaRetencionPorcentaje(val codigo: Int) {
        DIEZ_POR_CIENTO(9),
        VEINTE_POR_CIENTO(10),
        TRENTA_POR_CIENTO(1),
        CINQUENTA_POR_CIENTO(11),
        SETENTA_POR_CIENTO(2),
        CIEN_POR_CIENTO(3),
        RETENCION_EN_CERO(7),
        NO_PROCEDE_RETENCION(8)
    }
}

class ImpuestoRetencionIsd(
    portentaje: IsdRetencionPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.ISD, portentaje.codigo) {

    /**
     * Valores por el campo `códigoPorcentaje` por impuestos a retener IVA.
     *
     * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
     * Tabla 20.
     */
    enum class IsdRetencionPorcentaje(val codigo: Int) {
        CINCO_POR_CIENTO(4580),
        QUATRO_SETENTA_Y_CINCO_POR_CIENTO(4580),
        QUATRO_CINQUENTA_POR_CIENTO(4580),
        QUATRO_VEINTICINCO_POR_CIENTO(4580),
        QUATRO_POR_CIENTO(4580),
        // TODO: confirm if this is correct - all values are 4580?
        UNKNOWN(4580)
    }
}

typealias RentaPorcentaje = Int

class ImpuestoRetencionRenta(
    portentaje: RentaPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.RENTA, portentaje)

