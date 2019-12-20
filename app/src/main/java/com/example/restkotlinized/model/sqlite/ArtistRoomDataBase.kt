package com.example.restkotlinized.model.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.restkotlinized.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Results::class], version = 1, exportSchema = false)
abstract class ArtistRoomDataBase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    companion object {
        private const val databaseName = "product_database"

        @Volatile
        private var INSTANCE: ArtistRoomDataBase? = null

        fun getDatabase(context: Context): ArtistRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArtistRoomDataBase::class.java,
                    databaseName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
