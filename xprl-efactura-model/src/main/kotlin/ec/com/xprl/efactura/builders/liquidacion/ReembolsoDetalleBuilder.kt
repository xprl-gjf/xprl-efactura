package ec.com.xprl.efactura.builders.liquidacion

import ec.com.xprl.efactura.*
import ec.com.xprl.efactura.builders.AbstractBuilder
import ec.com.xprl.efactura.builders.requires

/**
 * Mutable builder for a [ReembolsoDetalle].
 */
class ReembolsoDetalleBuilder: AbstractBuilder<ReembolsoDetalleBuilder, ReembolsoDetalle>(
    ReembolsoDetalle::class.java,
    requires("proveedor") { it.proveedor },
    requires("tipoProveedorReembolso") { it.tipoProveedorReembolso },
    requires("docReembolso") { it.docReembolso },
    requires("detalleImpuestos") { it.impuestos }
) {
    private var proveedor: IdentityValue? = null
    private var paisPago: CodeValue? = null
    private var tipoProveedorReembolso: TipoProveedorReembolso? = null
    private var docReembolso: DocReembolso? = null
    private var impuestos: Map<ImpuestoIdentidad, ImpuestoDetalle>? = null

    fun setProveedor(value: IdentityValue) = apply { proveedor = value }
    fun setPaisPago(value: CodeValue?) = apply { paisPago = value }
    fun setTipoProveedorReembolso(value: TipoProveedorReembolso) = apply { tipoProveedorReembolso = value }
    fun setDocReembolso(value: DocReembolso) = apply { docReembolso = value }
    fun setImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply { impuestos = value }
    fun updateImpuestos(value: Map<ImpuestoIdentidad, ImpuestoDetalle>) = apply {
        impuestos = if (impuestos == null) { value } else { impuestos!! + value }
    }

    operator fun plus(other: ReembolsoDetalleBuilder) = merge(other)
    fun merge(other: ReembolsoDetalleBuilder) = apply {
        other.proveedor?.let { setProveedor(it) }
        other.paisPago?.let { setPaisPago(it) }
        other.tipoProveedorReembolso?.let { setTipoProveedorReembolso(it) }
        other.docReembolso?.let { setDocReembolso(it) }
        other.impuestos?.let { updateImpuestos(it) }
    }

    override fun validatedBuild() = ReembolsoDetalle(
        proveedor!!,
        paisPago,
        tipoProveedorReembolso!!,
        docReembolso!!,
        impuestos!!
    )
}
