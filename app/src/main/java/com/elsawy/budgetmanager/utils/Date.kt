package com.elsawy.budgetmanager.utils

import java.text.SimpleDateFormat
import java.util.*


fun convertTimeStampToDate(time: Long): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val netDate = Date(time)
    return sdf.format(netDate)
}