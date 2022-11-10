@file:Suppress("unused")

package ec.com.xprl.efactura


sealed class ImpuestoRetencionIdentidad(
    val tipoImpuesto: TipoImpuestoRetencion,
    val codigoPorcentaje: CodigoRetencion) {

    override fun toString() = "${tipoImpuesto}[${codigoPorcentaje.value}]"

    /**
     * Value equality comparison
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ImpuestoRetencionIdentidad
        return other.tipoImpuesto == this.tipoImpuesto &&
            other.codigoPorcentaje == this.codigoPorcentaje
    }

    override fun hashCode(): Int {
        return sequenceOf(
            tipoImpuesto.hashCode(),
            codigoPorcentaje.hashCode()
        ).fold(29) { acc, x -> acc * 37 + x }
    }

    companion object {
        /* empty - included solely to support extensions */
    }
}


data class ImpuestoRetencionValor(
    val baseImponible: UDecimalValue,
    val porcentajeRetener: UDecimalValue,
    val valorRetenido: UDecimalValue
)

/**
 * Valores por el campo `código` por impuestos a retener.
 *
 * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
 * Tabla 19 y también Tabla 22
 */
enum class TipoImpuestoRetencion(val codigo: Int) {
    RENTA(1),
    IVA(2),
    IVA_PRESUNTIVO_Y_RENTA(4),
    ISD(6);

    companion object {
        /* empty - included solely to support extensions */
    }
}

class ImpuestoRetencionIva(
    porcentaje: IvaRetencionPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.IVA, codigo(porcentaje.codigo)) {

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
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.ISD, codigo(portentaje.codigo)) {

    /**
     * Valores por el campo `códigoPorcentaje` por impuestos a retener IVA.
     *
     * Según [SRI Ficha técnica v2.21](https://www.sri.gob.ec/o/sri-portlet-biblioteca-alfresco-internet/descargar/435ca226-b48d-4080-bb12-bf03a54527fd/FICHA%20TE%cc%81CNICA%20COMPROBANTES%20ELECTRO%cc%81NICOS%20ESQUEMA%20OFFLINE%20Versio%cc%81n%202.21.pdf),
     * Tabla 20.
     */
    enum class IsdRetencionPorcentaje(val codigo: Int) {
        // TODO: confirm if this is correct - all values are 4580?
        CINCO_POR_CIENTO(4580),
        QUATRO_SETENTA_Y_CINCO_POR_CIENTO(4580),
        QUATRO_CINQUENTA_POR_CIENTO(4580),
        QUATRO_VEINTICINCO_POR_CIENTO(4580),
        QUATRO_POR_CIENTO(4580),
        UNKNOWN(4580)
    }
}

typealias RentaPorcentaje = Int

class ImpuestoRetencionRenta(
    porcentaje: RentaPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.RENTA, codigo(porcentaje))


class ImpuestoRetencionIvaPresuntivoYRenta(
    porcentaje: RentaPorcentaje
) : ImpuestoRetencionIdentidad(TipoImpuestoRetencion.IVA_PRESUNTIVO_Y_RENTA, codigo(porcentaje))


private fun codigo(value: Int) = CodigoRetencion.from(value)