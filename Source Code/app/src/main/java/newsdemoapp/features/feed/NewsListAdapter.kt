package newsdemoapp.features.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_feed_list_item.view.*
import newsdemoapp.R
import newsdemoapp.data.database.NewsDatabaseEntity

// Main adapter used for managing items list within the main RecyclerView (main feed listed)
class NewsListAdapter (val context: Context, val clickListener: (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var newsList: List<NewsDatabaseEntity> = ArrayList()

    fun setNews(newsList: List<NewsDatabaseEntity>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    fun changeSortingOrder() {
        this.newsList = newsList.reversed()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Prepare fetched data
        val title = newsList[position].title
        val description = newsList[position].description
        val urlToImage = newsList[position].urlToImage
        val publishedAt = newsList[position].publishedAt

        // Set the picture
        Glide.with(context)
            .load(urlToImage)
            .into(holder.picture)

        // Set data within the holder
        holder.title.text = title
        holder.description.text = description
        holder.date.text = publishedAt

        // Set onClickListener
        holder.rowContainer.setOnClickListener{
            val newsId = newsList[position].id
            clickListener(newsId)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val picture = view.imageView_picture
    val title = view.textView_title
    val description = view.textView_description
    val date = view.textView_date
    val rowContainer = view.row_container
}