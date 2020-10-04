package redditandroidapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.network.PostsNetworkInteractor
import redditandroidapp.data.repositories.PostsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PostsRepositoryTest {

    private var postsRepository: PostsRepository? = null
    private var fakePostsDatabaseEntity: PostsDatabaseEntity? = null
    private var fakeNewsEntitiesList = ArrayList<PostsDatabaseEntity>()

    @Mock
    private val postsDatabaseInteractor: PostsDatabaseInteractor? = null

    @Mock
    private val postsNetworkInteractor: PostsNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        postsRepository = PostsRepository(postsNetworkInteractor!!, postsDatabaseInteractor!!)

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
    fun fetchAllNewssByNewsRepository() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<List<PostsDatabaseEntity>>()
        newsEntityLiveData.setValue(fakeNewsEntitiesList);

        // Set testing conditions
        Mockito.`when`(postsDatabaseInteractor?.getAllPosts()).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = postsRepository?.getAllPosts(false)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }

    @Test
    fun fetchSingleNewsByNewsRepository() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<PostsDatabaseEntity>()
        newsEntityLiveData.setValue(fakePostsDatabaseEntity);

        // Prepare fake news id
        val fakeNewsId = 0

        // Set testing conditions
        Mockito.`when`(postsDatabaseInteractor?.getSingleSavedPostById(fakeNewsId))
            .thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = postsRepository?.getSingleSavedPostById(fakeNewsId)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}