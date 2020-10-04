package redditandroidapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.repositories.PostsRepository
import redditandroidapp.features.feed.FeedViewModel
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
    private var fakePostsDatabaseEntity: PostsDatabaseEntity? = null
    private var fakeNewsEntitiesList = ArrayList<PostsDatabaseEntity>()

    @Mock
    private val postsRepository: PostsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the country
        viewModel = FeedViewModel(postsRepository!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake News Entity (DB object)
        fakePostsDatabaseEntity = PostsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)

        // Prepare fake News Entities List
        fakeNewsEntitiesList.add(fakePostsDatabaseEntity!!)
    }

    @Test
    fun fetchAllNewsByFeedViewModel() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<List<PostsDatabaseEntity>>()
        newsEntityLiveData.setValue(fakeNewsEntitiesList)

        // Set testing conditions
        Mockito.`when`(postsRepository?.getAllPosts(false)).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = viewModel?.getAllPosts(false)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}