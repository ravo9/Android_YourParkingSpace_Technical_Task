package newsdemoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(NewsDatabaseEntity::class)], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}