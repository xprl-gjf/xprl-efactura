package uk.co.xprl.efactura.adapters

import java.math.BigDecimal

class Reembolso(val src: uk.co.xprl.efactura.Reembolso) {
    val totals: ReembolsoTotals = ReembolsoTotals(src.totals)

    val codDocReembolso: String
        get() = String.format("%02d", src.codDocReembolso.value)


    class ReembolsoTotals(val src: uk.co.xprl.efactura.Reembolso.ReembolsoTotals) {
        val totalComprobantesReembolso: BigDecimal
            get() = src.totalComprobantesReembolso.toBigDecimal()
        val totalBaseImponibleReembolso: BigDecimal
            get() = src.totalBaseImponibleReembolso.toBigDecimal()
        val totalImpuestoReembolso: BigDecimal
            get() = src.totalImpuestoReembolso.toBigDecimal()
    }
}