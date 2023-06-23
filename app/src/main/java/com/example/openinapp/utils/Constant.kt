package com.example.openinapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Constant {
    companion object {
        const val BASE_URL = "https://api.inopenapp.com/api/v1/"

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateFormat(inputTime: String): String {
            val dateTime = LocalDateTime.parse(inputTime, DateTimeFormatter.ISO_DATE_TIME)
            val outputFormat = DateTimeFormatter.ofPattern("d MMM yyyy")
            return dateTime.format(outputFormat)
        }
    }
}