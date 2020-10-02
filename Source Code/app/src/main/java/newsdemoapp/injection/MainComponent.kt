package newsdemoapp.injection

import dagger.Component
import newsdemoapp.data.database.NewsDatabaseInteractor
import newsdemoapp.data.network.NewsNetworkInteractor
import newsdemoapp.features.detailedview.DetailedViewFragment
import newsdemoapp.features.detailedview.DetailedViewViewModel
import newsdemoapp.features.feed.FeedActivity
import newsdemoapp.features.feed.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, FeedModule::class, ViewModelModule::class))
interface MainComponent {
    fun inject(feedActivity: FeedActivity)
    fun inject(detailedViewFragment: DetailedViewFragment)
    fun inject(feedViewModel: FeedViewModel)
    fun inject(detailedViewViewModel: DetailedViewViewModel)
    fun inject(newsNetworkInteractor: NewsNetworkInteractor)
    fun inject(newsDatabaseInteractor: NewsDatabaseInteractor)
}