package ec.com.xprl.efactura.adapters

import ec.com.xprl.efactura.DecimalValue
import ec.com.xprl.efactura.UDecimalValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal const val COMPROBANTE_ID = "comprobante"

private val simpleDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    .withZone(ZoneId.systemDefault())

private val monthAndYearDateFormatter = DateTimeFormatter.ofPattern("MM/yyyy")
    .withZone(ZoneId.systemDefault())


internal fun LocalDate.toDateString()
    = simpleDateFormatter.format(this.toJavaLocalDate())

internal fun LocalDate.toMonthAndYearDateString()
    = monthAndYearDateFormatter.format(this.toJavaLocalDate())

internal fun DecimalValue.toBigDecimal() = this
internal fun UDecimalValue.toBigDecimal() = this.value