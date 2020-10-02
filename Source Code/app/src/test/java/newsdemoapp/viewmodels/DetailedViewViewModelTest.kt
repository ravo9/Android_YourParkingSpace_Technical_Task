package newsdemoapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.repositories.NewsRepository
import newsdemoapp.features.detailedview.DetailedViewViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedViewViewModelTest {

    private var viewModel: DetailedViewViewModel? = null
    private var fakeNewsDatabaseEntity: NewsDatabaseEntity? = null

    @Mock
    private val newsRepository: NewsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the news
        viewModel = DetailedViewViewModel(newsRepository!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake News Entity (DB object)
        fakeNewsDatabaseEntity = NewsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)
    }

    @Test
    fun fetchSingleNewsByFeedViewModel() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<NewsDatabaseEntity>()
        newsEntityLiveData.setValue(fakeNewsDatabaseEntity);

        // Prepare fake news id
        val fakeNewsId = 0

        // Set testing conditions
        Mockito.`when`(newsRepository?.getSingleSavedNewsById(fakeNewsId)).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = viewModel?.getSingleSavedNewsById(fakeNewsId)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}