package com.example.android_skills.model.room

import androidx.room.*
import com.example.android_skills.model.Exhibit

@Dao
@TypeConverters(ImagesURLConverter::class)
interface ExhibitDao {
    @Query("SELECT * from exhibitions")
    suspend fun getExhibits(): List<Exhibit>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exhibits: List<Exhibit>): List<Long>

    @Query("DELETE FROM exhibitions")
    suspend fun deleteAllExhibits(): Int

// Next methods are created specially for instrumental unit-tests
//    @Query("DELETE FROM exhibitions WHERE id = (SELECT MAX(id) FROM exhibitions)")
//    suspend fun deleteLastItem(): Int

    @Query("SELECT COUNT(*) FROM exhibitions")
    suspend fun countOfRows(): Int
}