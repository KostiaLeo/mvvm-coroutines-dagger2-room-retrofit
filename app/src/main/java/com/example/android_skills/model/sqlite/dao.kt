package com.example.android_skills.model.sqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_skills.model.Results


@Dao
interface ArtistDao {
    @Query("SELECT * from artists_table")
    fun getArtists(): LiveData<List<Results>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artists: ArrayList<Results>)

    @Query("DELETE FROM artists_table")
    suspend fun deleteAllArtists()
}