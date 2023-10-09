package uk.co.xprl.efactura

data class Reembolso(
    val codDocReembolso: CodeValue,
    val totals: ReembolsoTotals
) {

    data class ReembolsoTotals(
        val totalComprobantesReembolso: UDecimalValue,
        val totalBaseImponibleReembolso: UDecimalValue,
        val totalImpuestoReembolso: UDecimalValue
    )
}