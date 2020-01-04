package com.example.android_skills.model.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_skills.model.Results

@Database(entities = [Results::class], version = 1, exportSchema = false)
abstract class ArtistRoomDataBase: RoomDatabase() {

    abstract fun artistDao(): ArtistDao

}
