package newsdemoapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import newsdemoapp.data.network.ApiClient
import newsdemoapp.data.network.ArticleGsonModel
import newsdemoapp.data.network.NewsNetworkInteractor
import newsdemoapp.data.network.NewsResponseGsonModel
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsNetworkInteractorTest {

    private var newsNetworkInteractor: NewsNetworkInteractor? = null
    private var fakeNewsResponseGsonModel: NewsResponseGsonModel? = null

    @Mock
    private val apiClient: ApiClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        newsNetworkInteractor = NewsNetworkInteractor(apiClient!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake Gson (API) model objects
        val fakeArticleGsonModel = ArticleGsonModel(title, description, url, imageUrl, publishingDate)
        fakeNewsResponseGsonModel = NewsResponseGsonModel(listOf(fakeArticleGsonModel!!))
    }

    // Tests to be implemented...
}
