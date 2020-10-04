package redditandroidapp.data.network

import retrofit2.Call
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/r/Android/hot.json")
    fun getPosts(): Call<PostsResponseGsonModel>
}