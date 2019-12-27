package com.example.android_skills.model

import androidx.room.*

data class MyNewz(val next: String, val results: Array<Results>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MyNewz

        if (next != other.next) return false
        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = next.hashCode()
        result = 31 * result + results.contentHashCode()
        return result
    }
}

@Entity(tableName = "artists_table")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Results(
    @Embedded val image: Image,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "name") val name: String,
    @Embedded val currency: Currency
) {
    @PrimaryKey(autoGenerate = true)
    var res_id: Int? = null
}

data class Image(val width: String, val url: String, val height: String) {
    @PrimaryKey(autoGenerate = true)
    var image_id: Int? = null
}

data class Currency(val id: String) {
    @PrimaryKey(autoGenerate = true)
    var cur_id: Int? = null
}