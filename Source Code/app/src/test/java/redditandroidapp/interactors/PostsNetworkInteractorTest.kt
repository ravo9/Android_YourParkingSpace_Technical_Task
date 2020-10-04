package redditandroidapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import redditandroidapp.data.network.ApiClient
import redditandroidapp.data.network.PostGsonModel
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.network.PostsResponseGsonModel
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PostsNetworkInteractorTest {

    private var postsNetworkInteractor: PostsNetworkInteractor? = null
    private var fakePostsResponseGsonModel: PostsResponseGsonModel? = null

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        postsNetworkInteractor = PostsNetworkInteractor(apiClient!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake Gson (API) model objects
        val fakeArticleGsonModel = PostGsonModel(title, description, url, imageUrl, publishingDate)
        fakePostsResponseGsonModel = PostsResponseGsonModel(listOf(fakeArticleGsonModel!!))
    }

    // Tests to be implemented...
}
