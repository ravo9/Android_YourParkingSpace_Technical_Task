package redditandroidapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import redditandroidapp.features.detailedview.DetailedViewViewModel
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
    private var fakePostsDatabaseEntity: PostsDatabaseEntity? = null

    @Mock
    private val postsRepository: PostsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the news
        viewModel = DetailedViewViewModel(postsRepository!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake News Entity (DB object)
        fakePostsDatabaseEntity = PostsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)
    }

    @Test
    fun fetchSingleNewsByFeedViewModel() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<PostsDatabaseEntity>()
        newsEntityLiveData.setValue(fakePostsDatabaseEntity);

        // Prepare fake news id
        val fakeNewsId = 0

        // Set testing conditions
        Mockito.`when`(postsRepository?.getSingleSavedPostById(fakeNewsId)).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = viewModel?.getSingleSavedPostById(fakeNewsId)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}