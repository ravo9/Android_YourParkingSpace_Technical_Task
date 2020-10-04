package redditandroidapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import redditandroidapp.data.network.PostGsonModel

// Interactor used for communication with the internal database
class PostsDatabaseInteractor(private val postsDatabase: PostsDatabase) {

    fun addNewPost(post: PostGsonModel?): LiveData<Boolean> {

        val savePostLiveData = MutableLiveData<Boolean>()

        post?.let {
            val postEntity = PostDatabaseEntity(
                permalink = it.permalink,
                title = it.title,
                thumbnail = it.thumbnail,
                author = it.author
            )
            GlobalScope.launch(Dispatchers.IO) {
                postsDatabase.getPostsDao().insertNewPost(postEntity)
            }
        }
        savePostLiveData.postValue(true)
        return savePostLiveData
    }

    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>? {
        return postsDatabase.getPostsDao().getSingleSavedPostById(id)
    }

    fun getAllPosts(): LiveData<List<PostDatabaseEntity>>? {
        return postsDatabase.getPostsDao().getAllSavedPosts()
    }

    fun clearDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            postsDatabase.getPostsDao().clearDatabase()
        }
    }
}



