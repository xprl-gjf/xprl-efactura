package uk.co.xprl.efactura.builders

import uk.co.xprl.efactura.*

/**
 * Mutable builder for a [ComprobanteDetalle].
 */
class ComprobanteDetalleBuilder: AbstractBuilder<ComprobanteDetalleBuilder, ComprobanteDetalle>(
    ComprobanteDetalle::class.java,
    requires("codigoPrincipal") { it.codigoPrincipal },
    requires("descripcion") { it.descripcion },
    requires("cantidad") { it.cantidad },
    requires("precioUnitario") { it.precioUnitario },
    requires("descuento") { it.descuento },
    requires("precioTotalSinImpuesto") { it.precioTotalSinImpuesto },
    requiresNotEmpty("impuestos") { it.impuestos },
    requiresNotMoreThan("detallesAdicionales", 3) { it.detallesAdicionales }
) {
    private var codigoPrincipal: AlphanumericCodeValue? = null
    private var descripcion: TextValue? = null
    private var cantidad: UDecimalValue? = null
    private var precioUnitario: UDecimalValue? = null
    private var descuento: UDecimalValue? = null
    private var precioTotalSinImpuesto: UDecimalValue? = null
    private var impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>? = null
    private var codigoAuxiliar: AlphanumericCodeValue? = null
    private var detallesAdicionales: DetalleAdicional? = null

    fun setCodigoPrincipal(value: AlphanumericCodeValue) = apply { codigoPrincipal = value }
    fun setDescripcion(value: TextValue) = apply { descripcion = value }
    fun setCantidad(value: UDecimalValue) = apply { cantidad = value }
    fun setPrecioUnitario(value: UDecimalValue) = apply { precioUnitario = value }
    fun setDescuento(value: UDecimalValue) = apply { descuento = value }
    fun setPrecioTotalSinImpuesto(value: UDecimalValue) = apply { precioTotalSinImpuesto = value }
    fun setImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply { impuestos = value }
    fun updateImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply {
        impuestos = if (impuestos == null) { value } else { impuestos!! + value }
    }
    fun setCodigoAuxiliar(value: AlphanumericCodeValue?) = apply { codigoAuxiliar = value }
    fun setDetallesAdicionales(vararg values: Pair<TextValue, TextValue>) = setDetallesAdicionales(values.toMap())
    fun setDetallesAdicionales(value: DetalleAdicional?) = apply { detallesAdicionales = value }
    fun updateDetallesAdicionales(value: DetalleAdicional) = apply {
        detallesAdicionales = if (detallesAdicionales == null) { value } else { detallesAdicionales!! + value }
    }

    operator fun plus(other: ComprobanteDetalleBuilder) = merge(other)
    fun merge(other: ComprobanteDetalleBuilder) = apply {
        other.codigoPrincipal?.let { setCodigoPrincipal(it) }
        other.descripcion?.let { setDescripcion(it) }
        other.cantidad?.let { setCantidad(it) }
        other.precioUnitario?.let { setPrecioUnitario(it) }
        other.descuento?.let { setDescuento(it) }
        other.precioTotalSinImpuesto?.let { setPrecioTotalSinImpuesto(it) }
        other.impuestos?.let { updateImpuestos(it) }
        other.codigoAuxiliar?.let { setCodigoAuxiliar(it) }
        other.detallesAdicionales?.let { updateDetallesAdicionales(it) }
    }

    override fun validatedBuild() = ComprobanteDetalle(
        codigoPrincipal!!,
        descripcion!!,
        cantidad!!,
        precioUnitario!!,
        descuento!!,
        precioTotalSinImpuesto!!,
        impuestos!!,
        codigoAuxiliar,
        detallesAdicionales
    )
}