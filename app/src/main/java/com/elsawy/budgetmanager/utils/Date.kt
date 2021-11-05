package com.elsawy.budgetmanager.utils

import java.text.SimpleDateFormat
import java.util.*


fun convertTimeStampToDate(time: Long): String {
   val sdf = SimpleDateFormat("dd/MM/yyyy")
   val netDate = Date(time)
   return sdf.format(netDate)
}

//fun getLastWeakDate(days: Int): Date {
//   val cal = Calendar.getInstance()
//   cal.add(Calendar.DAY_OF_YEAR, days)
//   return Date(cal.timeInMillis)
//}

private fun getCalculatedDate(days: Int): Date {
   val cal = Calendar.getInstance()
   cal.add(Calendar.DAY_OF_YEAR, days)
   return Date(cal.timeInMillis)
}

fun getLastWeekDate() = getCalculatedDate(-7)
fun getLastMonthDate() = getCalculatedDate(-30)
fun getLastYearDate() = getCalculatedDate(-365)
