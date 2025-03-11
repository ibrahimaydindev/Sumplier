package com.sumplier.app.data.helper

import android.os.Build
import androidx.annotation.RequiresApi
import com.sumplier.app.data.enums.TimeFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String?, inputFormat: TimeFormat?, outputFormat: TimeFormat?): String? {
        if (dateString.isNullOrBlank() || inputFormat == null || outputFormat == null) {
            return null
        }

        return try {
            val inputFormatter = DateTimeFormatter.ofPattern(inputFormat.pattern)
            val outputFormatter = DateTimeFormatter.ofPattern(outputFormat.pattern)

            val parsedDate = if (dateString.endsWith("Z")) {
                ZonedDateTime.parse(dateString, inputFormatter).toLocalDateTime()
            } else {
                LocalDateTime.parse(dateString, inputFormatter)
            }

            parsedDate.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
