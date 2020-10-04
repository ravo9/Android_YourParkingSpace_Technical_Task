package redditandroidapp.features.detailedview

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

class DetailedViewViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun getSingleSavedPostById(id: Int): LiveData<PostsDatabaseEntity>? {
        return postsRepository.getSingleSavedPostById(id)
    }
}