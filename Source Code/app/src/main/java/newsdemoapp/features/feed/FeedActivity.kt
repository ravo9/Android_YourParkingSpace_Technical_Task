package newsdemoapp.features.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import newsdemoapp.data.database.NewsDatabaseEntity
import newsdemoapp.features.detailedview.DetailedViewFragment
import newsdemoapp.injection.NewsListApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import newsdemoapp.R
import javax.inject.Inject

// Main ('feed') view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        NewsListApp.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (News List)
        setupRecyclerView()

        // Fetch feed items from the back-end and load them into the view
        subscribeForFeedItems()

        // Catch and handle potential network issues
        subscribeForNetworkError()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        main_feed_recyclerview.layoutManager = layoutManager
        newsListAdapter = NewsListAdapter(this, { newsId: Int ->
            displayDetailedView(newsId)
        })
        main_feed_recyclerview.adapter = newsListAdapter

        main_feed_recyclerview.setHasFixedSize(true);
        main_feed_recyclerview.setItemViewCacheSize(20);
        main_feed_recyclerview.setDrawingCacheEnabled(true);
        main_feed_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private fun subscribeForFeedItems() {
        viewModel.getAllNews(true)?.observe(this, Observer<List<NewsDatabaseEntity>> {

            if (!it.isNullOrEmpty()) {
                setViewState(STATE_CONTENT_LOADED)

                // Display fetched items
                newsListAdapter.setNews(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no items have been cached
            if (newsListAdapter.itemCount == 0) {
                setViewState(STATE_LOADING_ERROR)
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshNewsSubscription() {
        viewModel.getAllNews(true)?.removeObservers(this)
        subscribeForFeedItems()
    }

    private fun displayDetailedView(newsId: Int) {

        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putInt("newsId", newsId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> setupContentLoadedView()
        }
    }

    private fun setupLoadingErrorView() {
        // Stop the loading progress bar (circle)
        progressBar.visibility = View.INVISIBLE

        // Display "Try Again" button
        tryagain_button.visibility = View.VISIBLE

        // Setup onClick listener that resets news data subscription
        tryagain_button.setOnClickListener {
            refreshNewsSubscription()

            // Re-display the loading progress bar (circle)
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setupContentLoadedView() {
        // Hide the loading view
        loading_container.visibility = View.GONE
        appbar_container.visibility = View.VISIBLE

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshNewsSubscription()
        }

        // Setup sort button
        btn_sort.setOnClickListener{
            changeSortingOrder()
        }
    }

    private fun changeSortingOrder() {
        newsListAdapter.changeSortingOrder()
    }
}
