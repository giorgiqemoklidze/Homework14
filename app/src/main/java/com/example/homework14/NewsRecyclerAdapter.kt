package com.example.homework14

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework14.databinding.NewsItemCardViewBinding

class NewsRecyclerAdapter : PagingDataAdapter<NewsData, NewsRecyclerAdapter.NewsRecyclerViewHolder>(
    NEWS_COMPARATOR
) {

    private val newsList = mutableListOf<NewsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerViewHolder {
        return NewsRecyclerViewHolder(
            NewsItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsRecyclerViewHolder, position: Int) {
        holder.bind()
    }

    inner class NewsRecyclerViewHolder(private val binding: NewsItemCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentNewsData: NewsData

        fun bind() {
            currentNewsData = getItem(absoluteAdapterPosition)!!

            binding.newsUpdatedAtTextView.text = currentNewsData.updatedAt.toString()
            binding.titleTextView.text = currentNewsData.titleKA
            Glide.with(binding.newsCoverImageView.context).load(currentNewsData.cover)
                .into(binding.newsCoverImageView)

        }
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<NewsData>() {
            override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData) =
                oldItem == newItem
        }
    }

    fun setData(newsDataList: MutableList<NewsData>) {
        this.newsList.clear()
        this.newsList.addAll(newsDataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        newsList.clear()
        notifyDataSetChanged()
    }
}