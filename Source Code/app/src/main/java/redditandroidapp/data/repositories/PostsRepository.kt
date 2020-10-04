package redditandroidapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.database.PostDatabaseEntity
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.network.PostsResponseGsonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class PostsRepository @Inject constructor(private val networkInteractor: PostsNetworkInteractor,
                                          private val databaseInteractor: PostsDatabaseInteractor
) {

    fun getSingleSavedPostById(id: Int): LiveData<PostDatabaseEntity>? {
        return databaseInteractor.getSingleSavedPostById(id)
    }

    fun getAllPosts(backendUpdateRequired: Boolean): LiveData<List<PostDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllPosts()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return networkInteractor.getNetworkError()
    }

    fun setNetworkError(t: Throwable?) {
        networkInteractor.setNetworkError(t)
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {

        networkInteractor.getAllPosts().enqueue(object: Callback<PostsResponseGsonModel> {

            override fun onResponse(call: Call<PostsResponseGsonModel>?, response: Response<PostsResponseGsonModel>?) {

                // Clear database not to store outdated items
                databaseInteractor.clearDatabase()

                // Save freshly fetched items
                response?.body()?.data?.childrenPosts?.forEach {
                    databaseInteractor.addNewPost(it.post)
                }
            }

            override fun onFailure(call: Call<PostsResponseGsonModel>?, t: Throwable?) {
                setNetworkError(t)
            }
        })
    }
}