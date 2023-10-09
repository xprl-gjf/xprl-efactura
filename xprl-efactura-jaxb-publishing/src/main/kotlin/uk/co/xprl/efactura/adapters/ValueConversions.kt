package uk.co.xprl.efactura.adapters

import uk.co.xprl.efactura.DecimalValue
import uk.co.xprl.efactura.UDecimalValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val COMPROBANTE_ID = "comprobante"

private val simpleDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    .withZone(ZoneId.systemDefault())

private val monthAndYearDateFormatter = DateTimeFormatter.ofPattern("MM/yyyy")
    .withZone(ZoneId.systemDefault())


fun LocalDate.toDateString()
    = simpleDateFormatter.format(this.toJavaLocalDate())

fun LocalDate.toMonthAndYearDateString()
    = monthAndYearDateFormatter.format(this.toJavaLocalDate())

fun DecimalValue.toBigDecimal() = this
fun UDecimalValue.toBigDecimal() = this.value