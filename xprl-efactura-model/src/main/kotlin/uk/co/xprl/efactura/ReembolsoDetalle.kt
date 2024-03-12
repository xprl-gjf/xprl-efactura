package uk.co.xprl.efactura

data class ReembolsoDetalle(
    val proveedor: IdentityValue,
    val paisPago: CodeValue,
    val tipoProveedorReembolso: TipoProveedorReembolso,
    val docReembolso: DocReembolso,
    val impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>
)
