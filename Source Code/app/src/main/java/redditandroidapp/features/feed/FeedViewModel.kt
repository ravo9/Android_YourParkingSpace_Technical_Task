package redditandroidapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllPosts(backendUpdateRequired: Boolean): LiveData<List<PostsDatabaseEntity>>? {
        return postsRepository.getAllPosts(backendUpdateRequired)
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return postsRepository.getNetworkError()
    }
}