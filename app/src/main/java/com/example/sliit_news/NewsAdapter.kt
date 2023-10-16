package com.example.sliit_news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.sliit_news.R
import com.example.sliit_news.database.News
import com.example.sliit_news.database.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsAdapter(items: List<News>, repository: NewsRepository, viewModel: MainActivityData) :
    RecyclerView.Adapter<NewsViewHolder>() {

    private var context: Context? = null
    private var items = items
    private val repository = repository
    private val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        context = parent.context
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]

        // Display data
        holder.cbNews.text = currentItem.item
        holder.descriptionTextView.text = currentItem.description
        holder.dateTextView.text = currentItem.date

        holder.ivDelete.setOnClickListener {
            val isChecked = holder.cbNews.isChecked

            if (isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(currentItem)
                    val data = repository.getAllNewsItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
            } else {
                Toast.makeText(context, "Select the News to Be Deleted", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Function to update the data in the adapter
    fun updateData(newData: List<News>) {
        items = newData
        notifyDataSetChanged()
    }
}
