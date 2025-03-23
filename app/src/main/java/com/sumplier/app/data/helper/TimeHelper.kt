package com.sumplier.app.data.helper

import com.sumplier.app.data.enums.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale


object TimeHelper {
    fun convertFormat(dateString: String, fromFormat: DateFormat, toFormat: DateFormat): String? {
        return try {

            // Check 'Z'
            var dateStr = dateString
            if (fromFormat == DateFormat.CLOUD_FORMAT && !dateStr.endsWith("Z")) {
                dateStr += "Z" // 'Z' ekleyelim
            }

            val fromDateFormat = SimpleDateFormat(fromFormat.pattern, Locale.getDefault())
            val date = fromDateFormat.parse(dateStr)

            val toDateFormat = SimpleDateFormat(toFormat.pattern, Locale.getDefault())
            toDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
