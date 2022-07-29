@file:Suppress("MemberVisibilityCanBePrivate")

package ec.com.xprl.efactura.jaxb

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.jaxb.v1_0_0.createFactura as createFactura_1_0_0
import ec.com.xprl.efactura.jaxb.v1_1_0.createFactura as createFactura_1_1_0
import ec.com.xprl.efactura.jaxb.v1_0_0.createNotaCredito as createNotaCredito_1_0_0
import ec.com.xprl.efactura.jaxb.v1_1_0.createNotaCredito as createNotaCredito_1_1_0
import ec.com.xprl.efactura.jaxb.v1_0_0.createNotaDebito as createNotaDebito_1_0_0
import ec.com.xprl.efactura.jaxb.v1_0_0.createComprobanteRetencion as createComprobanteRetencion_1_0_0
import ec.com.xprl.efactura.jaxb.v1_0_0.createGuiaRemision as createGuiaRemision_1_0_0
import ec.com.xprl.efactura.jaxb.v1_1_0.createGuiaRemision as createGuiaRemision_1_1_0
import ec.com.xprl.efactura.jaxb.v1_0_0.createLiquidacionCompra as createLiquidacionCompra_1_0_0
import ec.com.xprl.efactura.jaxb.v1_1_0.createLiquidacionCompra as createLiquidacionCompra_1_1_0
import ec.gob.sri.factura.v1_0_0.Factura as Factura_1_0_0
import ec.gob.sri.factura.v1_1_0.Factura as Factura_1_1_0
import ec.gob.sri.credito.v1_0_0.NotaCredito as NotaCredito_1_0_0
import ec.gob.sri.credito.v1_1_0.NotaCredito as NotaCredito_1_1_0
import ec.gob.sri.debito.v1_0_0.NotaDebito as NotaDebito_1_0_0
import ec.gob.sri.retencion.v1_0_0.ComprobanteRetencion as ComprobanteRetencion_1_0_0
import ec.gob.sri.remision.v1_0_0.GuiaRemision as GuiaRemision_1_0_0
import ec.gob.sri.remision.v1_1_0.GuiaRemision as GuiaRemision_1_1_0
import ec.gob.sri.liquidacion.v1_0_0.LiquidacionCompra as LiquidacionCompra_1_0_0
import ec.gob.sri.liquidacion.v1_1_0.LiquidacionCompra as LiquidacionCompra_1_1_0


data class JaxbComprobante(
    val claveAcceso: ClaveAcceso,
    val jaxbObject: Any,
    val jaxbObjectClass: Class<*>
)

interface JaxbComprobanteFactory {
    fun <T: ComprobanteElectronico> createJaxbComprobante(comprobante: T, version: SchemaVersion<T>): JaxbComprobante
}

class JaxbComprobanteFactoryImpl(
    val ambiente: Ambiente,
    val tipoEmision: TipoEmisión = TipoEmisión.NORMAL,
    val genClaveAcceso: (ComprobanteElectronico, Ambiente, TipoEmisión) -> ClaveAcceso
) : JaxbComprobanteFactory {

    override fun <T: ComprobanteElectronico> createJaxbComprobante(comprobante: T, version: SchemaVersion<T>): JaxbComprobante {
        val claveAcceso = genClaveAcceso(comprobante, ambiente, tipoEmision)
        val (jaxbObject, jaxbObjectClass) = createJaxbComprobante(comprobante, version, ambiente, tipoEmision, claveAcceso)
        return JaxbComprobante(claveAcceso, jaxbObject, jaxbObjectClass)
    }

    companion object {
        @JvmStatic
        fun default(ambiente: Ambiente, tipoEmision: TipoEmisión = TipoEmisión.NORMAL) =
            JaxbComprobanteFactoryImpl(ambiente, tipoEmision, ClaveAccesoGeneratorImpl.Default::genClaveAcceso)

        private fun <T: ComprobanteElectronico> createJaxbComprobante(
            comprobante: T,
            version: SchemaVersion<T>,
            ambiente: Ambiente,
            tipoEmision: TipoEmisión,
            claveAcceso: ClaveAcceso
        ): Pair<Any, Class<*>> = when (version) {
            /* factura */
            SchemaVersion.FacturaV100 -> createFactura_1_0_0(
                comprobante as Factura, ambiente, tipoEmision, claveAcceso
            ) to Factura_1_0_0::class.java

            SchemaVersion.FacturaV110 -> createFactura_1_1_0(
                comprobante as Factura, ambiente, tipoEmision, claveAcceso
            ) to Factura_1_1_0::class.java

            // TODO: Factura_2_0_0
            // TODO: Factura_2_1_0

            /* nota credito */
            SchemaVersion.NotaCreditoV100 -> createNotaCredito_1_0_0(
                comprobante as NotaCredito, ambiente, tipoEmision, claveAcceso
            ) to NotaCredito_1_0_0::class.java

            SchemaVersion.NotaCreditoV110 -> createNotaCredito_1_1_0(
                comprobante as NotaCredito, ambiente, tipoEmision, claveAcceso
            ) to NotaCredito_1_1_0::class.java

            /* note debito */
            SchemaVersion.NotaDebitoV100 -> createNotaDebito_1_0_0(
                comprobante as NotaDebito, ambiente, tipoEmision, claveAcceso
            ) to NotaDebito_1_0_0::class.java

            /* comprobante retencion */
            SchemaVersion.ComprobanteRetencionV100 -> createComprobanteRetencion_1_0_0(
                comprobante as ComprobanteRetencion, ambiente, tipoEmision, claveAcceso
            ) to ComprobanteRetencion_1_0_0::class.java

            // TODO: ComprobanteRetencion_2_0_0

            /* guia de remision */
            SchemaVersion.GuiaRemisionV100 -> createGuiaRemision_1_0_0(
                comprobante as GuiaRemision, ambiente, tipoEmision, claveAcceso
            ) to GuiaRemision_1_0_0::class.java

            SchemaVersion.GuiaRemisionV110 -> createGuiaRemision_1_1_0(
                comprobante as GuiaRemision, ambiente, tipoEmision, claveAcceso
            ) to GuiaRemision_1_1_0::class.java

            /* liquidacion de compra */
            SchemaVersion.LiquidacionCompraV100 -> createLiquidacionCompra_1_0_0(
                comprobante as LiquidacionCompra, ambiente, tipoEmision, claveAcceso
            ) to LiquidacionCompra_1_0_0::class.java

            SchemaVersion.LiquidacionCompraV110 -> createLiquidacionCompra_1_1_0(
                comprobante as LiquidacionCompra, ambiente, tipoEmision, claveAcceso
            ) to LiquidacionCompra_1_1_0::class.java
        }
    }
}
