package newsdemoapp.data.network

import com.google.gson.annotations.SerializedName

// ApiResponse object used for deserializing data coming from API endpoint
data class NewsResponseGsonModel(
    @SerializedName("articles")
    val articles: List<ArticleGsonModel>?
)