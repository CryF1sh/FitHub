package com.example.fithub.models

import java.text.SimpleDateFormat
import java.util.Locale

data class UserProfile(
    val firstname: String?,
    val lastname: String?,
    val location: String?,
    val bio: String?,
    val birthdate: String?,
    val registrationdate: String?
){
    fun getFormattedCreationDate(): String? {
        return formatDate(registrationdate ?: "")
    }

    private fun formatDate(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateTimeString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

}

