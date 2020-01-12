package com.example.android_skills.model.model_module_description

import androidx.room.*
import com.example.android_skills.model.room.ImagesURLConverter


data class Exhibitions(
    val list: List<Exhibit>
)

@Entity(tableName = "exhibitions")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@TypeConverters(ImagesURLConverter::class)
data class Exhibit(

    val images: List<String>,

    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}