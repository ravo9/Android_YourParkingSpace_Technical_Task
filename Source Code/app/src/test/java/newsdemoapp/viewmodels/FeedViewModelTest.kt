package newsdemoapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.repositories.NewsRepository
import newsdemoapp.features.feed.FeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FeedViewModelTest {

    private var viewModel: FeedViewModel? = null
    private var fakeNewsDatabaseEntity: NewsDatabaseEntity? = null
    private var fakeNewsEntitiesList = ArrayList<NewsDatabaseEntity>()

    @Mock
    private val newsRepository: NewsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the country
        viewModel = FeedViewModel(newsRepository!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake News Entity (DB object)
        fakeNewsDatabaseEntity = NewsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)

        // Prepare fake News Entities List
        fakeNewsEntitiesList.add(fakeNewsDatabaseEntity!!)
    }

    @Test
    fun fetchAllNewsByFeedViewModel() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<List<NewsDatabaseEntity>>()
        newsEntityLiveData.setValue(fakeNewsEntitiesList)

        // Set testing conditions
        Mockito.`when`(newsRepository?.getAllNews(false)).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = viewModel?.getAllNews(false)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}