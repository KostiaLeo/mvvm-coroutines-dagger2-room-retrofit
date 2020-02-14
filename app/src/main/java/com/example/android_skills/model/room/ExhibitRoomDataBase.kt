package com.example.android_skills.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_skills.model.Exhibit
// DataBase class (provides in dagger.source.source.module)

@Database(entities = [Exhibit::class], version = 1, exportSchema = false)
abstract class ExhibitRoomDataBase: RoomDatabase() {

    abstract fun exhibitDao(): ExhibitDao

}