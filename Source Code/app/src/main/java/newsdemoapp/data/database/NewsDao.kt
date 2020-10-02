package newsdemoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewNews(newsDatabaseEntity: NewsDatabaseEntity)

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    fun getSingleSavedNewsById(id: Int): LiveData<NewsDatabaseEntity>?

    @Query("SELECT * FROM news")
    fun getAllSavedNews(): LiveData<List<NewsDatabaseEntity>>?

    @Query("DELETE FROM news")
    fun clearDatabase()
}