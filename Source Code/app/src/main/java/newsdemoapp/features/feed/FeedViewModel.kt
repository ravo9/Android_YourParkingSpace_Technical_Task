package newsdemoapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.repositories.NewsRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val newsRepository: NewsRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllNews(backendUpdateRequired: Boolean): LiveData<List<NewsDatabaseEntity>>? {
        return newsRepository.getAllNews(backendUpdateRequired)
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return newsRepository.getNetworkError()
    }
}