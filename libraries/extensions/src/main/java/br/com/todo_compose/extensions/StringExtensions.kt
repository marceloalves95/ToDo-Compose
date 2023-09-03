package br.com.todo_compose.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.reducedDate(): String {
    val local = Locale("pt", "BR")
    val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = LocalDate.parse(this, dtf)
    val day = localDate.dayOfMonth.toString()
    val month = localDate.month
    val reducedMonth = DateTimeFormatter.ofPattern("MMM", local)
    val completedMonth = reducedMonth.format(month)
    return "$day de $completedMonth"
}