package ec.com.xprl.efactura.test

import ec.com.xprl.efactura.*
import kotlinx.datetime.LocalDate

internal val defaultSecuencial = SecuencialValue.from(1)
internal val defaultFechaEmision = LocalDate(2022, 1, 1)
internal val defaultSchemaVersion = SchemaVersion.FacturaV100
internal val defaultAmbiente = Ambiente.PRUEBAS
internal val defaultEmisor = Emisor(
    RUC = IdentityValue.RUC.from("0123456789001"),
    razónSocial = TextValue.from("PRUEBAS SERVICIO DE RENTAS INTERNAS"),
    direccionMatriz = TextValue.from("DIRECCION MATRIZ"),
    códigoEstablecimiento = CodeValue.from(1),
    códigoPuntoEmisión = CodeValue.from(1)
)
internal val defaultComprador = Comprador(
    identificación = IdentityValue.RUC.from("0123456789001"),
    razónSocial = TextValue.from("PRUEBAS SERVICIO DE RENTAS INTERNAS")
)
internal val defaultTotalImpuestos = mapOf(
    ImpuestoIdentidad(
        tipoImpuesto = TipoImpuesto.ICE,
        codigoPorcentaje = 1
    ) to ImpuestoTotal(
        baseImponible = UDecimalValue(12.5),
        valor = UDecimalValue(10.0)
    )
)
internal val defaultTotals = Factura.Totals(
    totalSinImpuestos = UDecimalValue(0),
    totalDescuento = UDecimalValue(0),
    totalConImpuestos = defaultTotalImpuestos,
    importeTotal = UDecimalValue(0)
)
internal val defaultPagos = listOf(
    Pago(formaPago = FormaDePago.TARJETA_DE_DEBITO, total = UDecimalValue(1.0))
)
internal val defaultImpuestos = mapOf(
    ImpuestoIdentidad(
        tipoImpuesto = TipoImpuesto.ICE,
        codigoPorcentaje = 1
    ) to ImpuestoDetalle(
        tarifa = UDecimalValue(12.5),
        baseImponible = UDecimalValue(12.5),
        valor = UDecimalValue(10.0)
    )
)
internal val defaultDetalles = listOf(
    ComprobanteDetalle(
        codigoPrincipal = AlphanumericCodeValue.from("ABC-123"),
        descripcion = TextValue.from("FACTURA DETALLE"),
        cantidad = UDecimalValue(1),
        precioUnitario = UDecimalValue(99.9),
        descuento = UDecimalValue(0),
        precioTotalSinImpuesto = UDecimalValue(99.9),
        impuestos = defaultImpuestos
    )
)
