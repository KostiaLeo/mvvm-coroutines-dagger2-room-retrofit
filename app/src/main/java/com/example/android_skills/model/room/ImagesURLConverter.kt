package com.example.android_skills.model.room

import androidx.room.TypeConverter
import java.util.stream.Collectors

// Type converters for properly Room database working

class ImagesURLConverter {
    private val delimiter = ", "

    @TypeConverter
    fun fromImages(images: List<String>): String = images.stream().collect(Collectors.joining(delimiter))

    @TypeConverter
    fun fromSavedString(data: String): List<String> = data.split(delimiter)
}