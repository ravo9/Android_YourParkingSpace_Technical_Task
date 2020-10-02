package newsdemoapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import newsdemoapp.data.database.NewsDao
import newsdemoapp.data.database.NewsDatabase
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.database.NewsDatabaseInteractor
import newsdemoapp.data.network.ArticleGsonModel
import newsdemoapp.data.network.NewsResponseGsonModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsDatabaseInteractorTest {

    private var newsDatabaseInteractor: NewsDatabaseInteractor? = null
    private var fakeArticleGsonModel: ArticleGsonModel? = null
    private var fakeNewsResponseGsonModel: NewsResponseGsonModel? = null
    private var fakeNewsDatabaseEntity: NewsDatabaseEntity? = null

    @Mock
    private val newsDatabase: NewsDatabase? = null

    @Mock
    private val newsDao: NewsDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        newsDatabaseInteractor = NewsDatabaseInteractor(newsDatabase!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake Gson (API) model objects
        fakeArticleGsonModel = ArticleGsonModel(title, description, url, imageUrl, publishingDate)
        fakeNewsResponseGsonModel = NewsResponseGsonModel(listOf(fakeArticleGsonModel!!))

        // Prepare fake News Entity (DB object)
        fakeNewsDatabaseEntity = NewsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)
    }

    @Test
    fun saveNewsByDatabaseInteractor() {

        // Perform the action
        val resultStatus = newsDatabaseInteractor!!.addNewNews(fakeArticleGsonModel).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchNewsByDatabaseInteractor() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<NewsDatabaseEntity>()
        newsEntityLiveData.setValue(fakeNewsDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(newsDatabase?.getNewsDao()).thenReturn(newsDao)
        Mockito.`when`(newsDao?.getSingleSavedNewsById(anyInt())).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = newsDatabaseInteractor?.getSingleSavedNewsById(0)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}