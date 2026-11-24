package com.example.myapplication.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun today(): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date())
    }

    fun addDays(days: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, days)
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun addMonths(months: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, months)
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun addYears(years: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, years)
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun isExpiringSoon(expiryDateStr: String, daysThreshold: Int = 7): Boolean {
        return try {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val expiryDate = sdf.parse(expiryDateStr) ?: return false
            val calToday = Calendar.getInstance()
            val calThreshold = Calendar.getInstance()
            calThreshold.add(Calendar.DAY_OF_MONTH, daysThreshold)
            expiryDate.after(calToday.time) && expiryDate.before(calThreshold.time)
        } catch (e: Exception) {
            false
        }
    }

    fun isExpired(expiryDateStr: String): Boolean {
        return try {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            val expiryDate = sdf.parse(expiryDateStr) ?: return false
            val today = Date()
            expiryDate.before(today)
        } catch (e: Exception) {
            false
        }
    }
}
