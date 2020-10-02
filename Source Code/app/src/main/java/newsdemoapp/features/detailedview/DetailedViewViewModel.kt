package newsdemoapp.features.detailedview

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.repositories.NewsRepository
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val newsRepository: NewsRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedNewsById(id: Int): LiveData<NewsDatabaseEntity>? {
        return newsRepository.getSingleSavedNewsById(id)
    }
}