package newsdemoapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import newsdemoapp.data.database.NewsDatabaseInteractor
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.network.ArticleGsonModel
import newsdemoapp.data.network.NewsNetworkInteractor
import newsdemoapp.data.network.NewsResponseGsonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class NewsRepository @Inject constructor(private val newsNetworkInteractor: NewsNetworkInteractor,
                                         private val databaseInteractor: NewsDatabaseInteractor
) {

    fun getSingleSavedNewsById(id: Int): LiveData<NewsDatabaseEntity>? {
        return databaseInteractor.getSingleSavedNewsById(id)
    }

    fun getAllNews(backendUpdateRequired: Boolean): LiveData<List<NewsDatabaseEntity>>? {
        if (backendUpdateRequired) {
            updateDataFromBackEnd()
        }
        return databaseInteractor.getAllNews()
    }

    fun getNetworkError(): LiveData<Boolean>? {
        return newsNetworkInteractor.getNetworkError()
    }

    fun setNetworkError(t: Throwable?) {
        newsNetworkInteractor.setNetworkError(t)
    }

    @SuppressLint("CheckResult")
    private fun updateDataFromBackEnd() {

        newsNetworkInteractor.getAllNews().enqueue(object: Callback<NewsResponseGsonModel> {

            override fun onResponse(call: Call<NewsResponseGsonModel>?, response: Response<NewsResponseGsonModel>?) {

                // Clear database not to store outdated items
                databaseInteractor.clearDatabase()

                // Save freshly fetched items
                response?.body()?.articles?.forEach {
                    databaseInteractor.addNewNews(it)
                }
            }

            override fun onFailure(call: Call<NewsResponseGsonModel>?, t: Throwable?) {
                setNetworkError(t)
            }
        })
    }
}