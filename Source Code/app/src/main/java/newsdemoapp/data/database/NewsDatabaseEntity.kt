package newsdemoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsDatabaseEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val title: String?,
        val description: String?,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?
)

