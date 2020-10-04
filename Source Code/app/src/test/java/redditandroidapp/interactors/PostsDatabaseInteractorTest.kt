package redditandroidapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import redditandroidapp.data.database.PostsDao
import redditandroidapp.data.database.PostsDatabase
import redditandroidapp.data.database.PostsDatabaseEntity
import redditandroidapp.data.database.PostsDatabaseInteractor
import redditandroidapp.data.network.PostGsonModel
import redditandroidapp.data.network.PostsResponseGsonModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PostsDatabaseInteractorTest {

    private var postsDatabaseInteractor: PostsDatabaseInteractor? = null
    private var fakePostGsonModel: PostGsonModel? = null
    private var fakePostsResponseGsonModel: PostsResponseGsonModel? = null
    private var fakePostsDatabaseEntity: PostsDatabaseEntity? = null

    @Mock
    private val postsDatabase: PostsDatabase? = null

    @Mock
    private val postsDao: PostsDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        postsDatabaseInteractor = PostsDatabaseInteractor(postsDatabase!!)

        // Prepare fake data
        val id = 0
        val title = "fake/news/title"
        val description = "fake/news/description"
        val url = "fake/news/url"
        val imageUrl = "fake/news/image/url"
        val publishingDate = "fake/news/publishing/date"

        // Prepare fake Gson (API) model objects
        fakePostGsonModel = PostGsonModel(title, description, url, imageUrl, publishingDate)
        fakePostsResponseGsonModel = PostsResponseGsonModel(listOf(fakePostGsonModel!!))

        // Prepare fake News Entity (DB object)
        fakePostsDatabaseEntity = PostsDatabaseEntity(id, title, description, url, imageUrl, publishingDate)
    }

    @Test
    fun saveNewsByDatabaseInteractor() {

        // Perform the action
        val resultStatus = postsDatabaseInteractor!!.addNewPost(fakePostGsonModel).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchNewsByDatabaseInteractor() {

        // Prepare LiveData structure
        val newsEntityLiveData = MutableLiveData<PostsDatabaseEntity>()
        newsEntityLiveData.setValue(fakePostsDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(postsDatabase?.getPostsDao()).thenReturn(postsDao)
        Mockito.`when`(postsDao?.getSingleSavedPostById(anyInt())).thenReturn(newsEntityLiveData)

        // Perform the action
        val storedNews = postsDatabaseInteractor?.getSingleSavedPostById(0)

        // Check results
        Assert.assertSame(newsEntityLiveData, storedNews);
    }
}