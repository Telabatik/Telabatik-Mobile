package com.dicoding.telabatik

import android.text.format.DateUtils
import java.util.Date

fun getRelativeTimeString(date: Date): String {
    val now = System.currentTimeMillis()
    val time = date.time
    val diff = now - time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7
    val months = weeks / 4
    val years = months / 12

    return when {
        years > 0 -> "$years tahun yang lalu"
        months > 0 -> "$months bulan yang lalu"
        weeks > 0 -> "$weeks minggu yang lalu"
        days > 0 -> "$days hari yang lalu"
        hours > 0 -> "$hours jam yang lalu"
        minutes > 0 -> "$minutes menit yang lalu"
        else -> "$seconds detik yang lalu"
    }
}
