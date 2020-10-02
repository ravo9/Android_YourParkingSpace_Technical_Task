package newsdemoapp.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import newsdemoapp.data.database.NewsDatabase
import newsdemoapp.data.database.NewsDatabaseInteractor
import newsdemoapp.data.network.ApiClient
import newsdemoapp.data.network.NewsNetworkInteractor
import newsdemoapp.data.network.NetworkAdapter
import newsdemoapp.data.repositories.NewsRepository
import javax.inject.Singleton

@Module
class FeedModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Context): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "main_database").build()
    }

    @Provides
    @Singleton
    fun providesNewsDatabaseInteractor(newsDatabase: NewsDatabase): NewsDatabaseInteractor {
        return NewsDatabaseInteractor(newsDatabase)
    }

    @Provides
    @Singleton
    fun providesNewsNetworkInteractor(apiClient: ApiClient): NewsNetworkInteractor {
        return NewsNetworkInteractor(apiClient)
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return NetworkAdapter.apiClient()
    }

    @Provides
    @Singleton
    fun providesNewsRepository(newsNetworkInteractor: NewsNetworkInteractor,
                                    newsDatabaseInteractor: NewsDatabaseInteractor
    ): NewsRepository {
        return NewsRepository(newsNetworkInteractor, newsDatabaseInteractor)
    }
}