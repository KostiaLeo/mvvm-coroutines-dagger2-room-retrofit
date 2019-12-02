package com.example.restkotlinized.model.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.restkotlinized.model.remote.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Results::class), version = 1, exportSchema = false)
abstract class ProductRoomDataBase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    companion object {

        @Volatile
        private var INSTANCE: ProductRoomDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ProductRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDataBase::class.java,
                    "product_database"
                )
                    .addCallback(
                        ProductDatabaseCallback(scope)
                    ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class ProductDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    cleanDatabase(database.artistDao())
                }
            }
        }

        suspend fun cleanDatabase(artistDao: ArtistDao) {
            if (artistDao.getArtists().value?.size != 0 && artistDao.getArtists().value?.size != null) {
                artistDao.deleteAllArtists()
            }
        }
    }
}
