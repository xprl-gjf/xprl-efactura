package ec.com.xprl.efactura

data class ComprobanteDetalle(
    val codigoPrincipal: AlphanumericCodeValue,
    val descripcion: TextValue,
    val cantidad: UDecimalValue,
    val precioUnitario: UDecimalValue,
    val descuento: UDecimalValue,
    val precioTotalSinImpuesto: UDecimalValue,
    val impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>,
    val codigoAuxiliar: AlphanumericCodeValue? = null,
    val detallesAdicionales: List<DetalleAdicionale>? = null
)