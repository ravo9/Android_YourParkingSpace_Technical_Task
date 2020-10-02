package newsdemoapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import newsdemoapp.data.network.ArticleGsonModel

// Interactor used for communication with the internal database
class NewsDatabaseInteractor(private val newsDatabase: NewsDatabase) {

    fun addNewNews(news: ArticleGsonModel?): LiveData<Boolean> {

        val saveNewsLiveData = MutableLiveData<Boolean>()

        news?.let {
            val newsEntity = NewsDatabaseEntity(
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt
            )
            GlobalScope.launch(Dispatchers.IO) {
                newsDatabase.getNewsDao().insertNewNews(newsEntity)
            }
        }
        saveNewsLiveData.postValue(true)
        return saveNewsLiveData
    }

    fun getSingleSavedNewsById(id: Int): LiveData<NewsDatabaseEntity>? {
        return newsDatabase.getNewsDao().getSingleSavedNewsById(id)
    }

    fun getAllNews(): LiveData<List<NewsDatabaseEntity>>? {
        return newsDatabase.getNewsDao().getAllSavedNews()
    }

    fun clearDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            newsDatabase.getNewsDao().clearDatabase()
        }
    }
}



