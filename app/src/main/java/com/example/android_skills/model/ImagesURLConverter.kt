package com.example.android_skills.model

import androidx.room.TypeConverter
import java.util.stream.Collectors

class ImagesURLConverter {
    private val delimiter = ", "

    @TypeConverter
    fun fromImages(images: List<String>): String = images.stream().collect(Collectors.joining(delimiter))

    @TypeConverter
    fun fromSavedString(data: String): List<String> = data.split(delimiter)
}