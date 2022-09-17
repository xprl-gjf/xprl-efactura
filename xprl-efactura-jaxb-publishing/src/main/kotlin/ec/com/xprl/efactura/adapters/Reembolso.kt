package ec.com.xprl.efactura.adapters

import java.math.BigDecimal

internal class Reembolso(val src: ec.com.xprl.efactura.Reembolso) {
    val totals: ReembolsoTotals = ReembolsoTotals(src.totals)

    val codDocReembolso: String
        get() = String.format("%02d", src.codDocReembolso.value)


    internal class ReembolsoTotals(val src: ec.com.xprl.efactura.Reembolso.ReembolsoTotals) {
        val totalComprobantesReembolso: BigDecimal
            get() = src.totalComprobantesReembolso.toBigDecimal()
        val totalBaseImponibleReembolso: BigDecimal
            get() = src.totalBaseImponibleReembolso.toBigDecimal()
        val totalImpuestoReembolso: BigDecimal
            get() = src.totalImpuestoReembolso.toBigDecimal()
    }
}