package com.elsawy.budgetmanager.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertTimeStampToDate(time: Long): String {
   val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
   val netDate = Date(time)
   return sdf.format(netDate)
}

fun getCalculatedDate(days: Int): Date {
   val cal = Calendar.getInstance()
   cal.add(Calendar.DAY_OF_YEAR, days)
   return Date(cal.timeInMillis)
}

abstract class DateFilter {
   abstract fun getDate(): Date
   abstract fun getDateName(date: Long): String
}

class WeekFilter : DateFilter() {
   override fun getDate() = getCalculatedDate(-7)
   override fun getDateName(date: Long): String {
      return SimpleDateFormat("EE", Locale.ENGLISH).format(Date(date))
   }
}

class MonthFilter : DateFilter() {
   override fun getDate() = getCalculatedDate(-30)
   override fun getDateName(date: Long): String {
      val cal = Calendar.getInstance()
      cal.time = Date(date)
      val month = cal[Calendar.MONTH] + 1
      val day = cal[Calendar.DAY_OF_MONTH]

      return "$day-$month"
   }
}

class YearFilter : DateFilter() {
   override fun getDate() = getCalculatedDate(-365)
   override fun getDateName(date: Long): String {
      val cal = Calendar.getInstance()
      cal.time = Date(date)
      val year = cal[Calendar.YEAR]
      val month = cal[Calendar.MONTH] + 1

      return "$month-$year"
   }
}