package com.example.example.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.example.data.model.Article
import com.example.example.databinding.ItemNewsBinding

class NewsAdapter : ListAdapter<Article, NewsAdapter.ViewHolder>(
    ContractTermsDiffCallback()
) {

    inner class ViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.titleTextView.text = item.title
    }
}

class ContractTermsDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ) = oldItem.title === newItem.title

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ) = oldItem.title == newItem.title
}