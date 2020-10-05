package redditandroidapp.features.feed

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import javax.inject.Inject

class FeedViewModel @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel(), LifecycleObserver {

    fun getAllPosts(backendUpdateRequired: Boolean): LiveData<List<PostDatabaseEntity>>? {
        return postsRepository.getAllPosts(backendUpdateRequired)
    }

    fun subscribeForUpdateErrors(): LiveData<Boolean>? {
        return postsRepository.subscribeForUpdateErrors()
    }
}