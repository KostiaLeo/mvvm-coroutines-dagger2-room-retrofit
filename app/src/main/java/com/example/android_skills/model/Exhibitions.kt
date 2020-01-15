package com.example.android_skills.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import androidx.room.TypeConverters
import com.example.android_skills.model.room.ImagesURLConverter


data class Exhibitions(
    val list: List<Exhibit>
)

@Entity(tableName = "exhibitions")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@TypeConverters(ImagesURLConverter::class)
data class Exhibit(

    val images: List<String>,

    @PrimaryKey
    val title: String
)