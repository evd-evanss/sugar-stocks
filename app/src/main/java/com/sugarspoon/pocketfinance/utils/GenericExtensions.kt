package com.sugarspoon.pocketfinance.utils

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun String.getDateTime(): String {
    return if(this.isEmpty()) this
    else try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(this)
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy - hh:mm a", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)
    } catch (e: Exception) {
        "00/00/0000 - 00:00"
    }
}

@SuppressLint("ConstantLocale")
val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).configure()

fun NumberFormat.configure() = apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
}

fun Number.asCurrency(): String {
    return currencyFormatter.format(this)
}

fun String.addCurrencySuffix(): String {
    val points = this.fold(0) { acc, c ->
        if(c == '.') acc + 1 else acc + 0
    }
    return when (points) {
        2 -> this.split(".").first() + " mi"
        3 -> this.split(".").first() + " bi"
        4 -> this.split(".").first() + " tri"
        else -> this
    }
}

fun String.formatToCurrency(): String {
    return format(locale = Locale.getDefault(), "%.2f").replace(".", ",")
}

fun Double.formatWith2DecimalPlaces(): String {
    return String.format(locale = Locale.getDefault(), format = "%.2f", this)
}

fun Number?.formatToPercentage(): String {
    return if(this != null) String.format( "%.2f",this) else ""
}