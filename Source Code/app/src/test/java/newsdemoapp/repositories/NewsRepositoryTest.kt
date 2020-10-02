package newsdemoapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import newsdemoapp.data.database.NewsDatabaseInteractor
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.data.network.NewsNetworkInteractor
import newsdemoapp.data.repositories.NewsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsRepositoryTest {

    private var newsRepository: NewsRepository? = null
    private var fakeNewsDatabaseEntity: NewsDatabaseEntity? = null
    private var fakeNewsEntitiesList = ArrayList<NewsDatabaseEntity>()

    @Mock
    private val newsDatabaseInteractor: NewsDatabaseInteractor? = null

    @Mock
    private val newsNetworkInteractor: NewsNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        newsRepository = NewsRepository(newsNetworkInteractor!!, newsDatabaseInteractor!!)

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
    fun fetchAllNewssByNewsRepository() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<List<NewsDatabaseEntity>>()
        newsEntityLiveData.setValue(fakeNewsEntitiesList);

        // Set testing conditions
        Mockito.`when`(newsDatabaseInteractor?.getAllNews()).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = newsRepository?.getAllNews(false)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }

    @Test
    fun fetchSingleNewsByNewsRepository() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<NewsDatabaseEntity>()
        newsEntityLiveData.setValue(fakeNewsDatabaseEntity);

        // Prepare fake news id
        val fakeNewsId = 0

        // Set testing conditions
        Mockito.`when`(newsDatabaseInteractor?.getSingleSavedNewsById(fakeNewsId))
            .thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = newsRepository?.getSingleSavedNewsById(fakeNewsId)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}