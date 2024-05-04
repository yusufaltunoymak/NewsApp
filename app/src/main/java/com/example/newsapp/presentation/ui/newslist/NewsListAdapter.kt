import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ListRecyclerItemBinding
import com.example.newsapp.util.cleanText
import com.example.newsapp.util.downloadFromUrl

class NewsListAdapter(private val context: Context) : PagingDataAdapter<Article, NewsListAdapter.ViewHolder>(NewsListCallBack()) {

    inner class ViewHolder(private val binding: ListRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            article?.let {
                binding.apply {
                    if (!article.urlToImage.isNullOrEmpty()) {
                        newsPosterImage.downloadFromUrl(article.urlToImage, context, R.drawable.news_logo)
                        Glide.with(itemView.context).load(article.urlToImage).into(newsPosterImage)
                    }
                    newsHeaderText.text = article.title
                    newsContentText.text = cleanText(article.content)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }
}
class NewsListCallBack : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
