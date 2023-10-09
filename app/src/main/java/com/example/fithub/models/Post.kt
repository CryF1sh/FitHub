package com.example.fithub.models

import java.text.SimpleDateFormat
import java.util.Locale

data class Post(
    val postId: Int,
    val title: String,
    val creatorFirstName: String,
    val creatorLastName: String,
    val creationDate: String,
    val titleImageId: Int?,
){
    fun getFormattedCreationDate(): String {
        val formattedDate = formatDate(creationDate)
        return formattedDate
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
