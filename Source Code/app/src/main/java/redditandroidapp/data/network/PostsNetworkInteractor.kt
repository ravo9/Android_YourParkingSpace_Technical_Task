package redditandroidapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import javax.inject.Inject

// Interactor used for communication with the external API
class PostsNetworkInteractor @Inject constructor(var apiClient: ApiClient) {

    private val updateError: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllPosts(): Call<PostsResponseGsonModel> {
        return apiClient.getPosts()
    }

    fun getUpdateError(): LiveData<Boolean>? {
        return updateError
    }

    fun setUpdateError(t: Throwable?) {
        updateError.postValue(true)
        if (t != null) { Log.e("Network Error: ", t.message) }
    }
}