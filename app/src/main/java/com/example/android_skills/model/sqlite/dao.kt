package com.example.android_skills.model.sqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_skills.model.Results
import io.reactivex.Flowable
import javax.inject.Inject


@Dao
interface ArtistDao {
    @Query("SELECT * from artists_table")
    fun getArtists(): Flowable<List<Results>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artists: ArrayList<Results>): List<Long>

    @Query("DELETE FROM artists_table")
    suspend fun deleteAllArtists(): Int

    @Query("DELETE FROM artists_table WHERE res_id = (SELECT MAX(res_id) FROM artists_table)")
    suspend fun deleteLastItem(): Int

    @Query("SELECT COUNT(*) FROM artists_table")
    suspend fun countOfRows(): Int
}