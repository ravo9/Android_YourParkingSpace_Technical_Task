package newsdemoapp.data.network

import retrofit2.Call
import retrofit2.http.GET

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET("/v2/top-headlines?country=us&apiKey=dee1853a2b3f487ca677bbe49074b21c")
    fun getNews(): Call<NewsResponseGsonModel>
}