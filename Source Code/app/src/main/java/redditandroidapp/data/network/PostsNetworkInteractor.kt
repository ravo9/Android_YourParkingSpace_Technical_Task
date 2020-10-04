package redditandroidapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import javax.inject.Inject

// Interactor used for communication with the external API
class PostsNetworkInteractor @Inject constructor(var apiClient: ApiClient) {

    private val networkError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllPosts(): Call<PostsResponseGsonModel> {
        return apiClient.getPosts()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return networkError
    }

    fun setNetworkError(t: Throwable?) {
        networkError.postValue(true)
        if (t != null) { Log.e("Network Error: ", t.message) }
    }
}