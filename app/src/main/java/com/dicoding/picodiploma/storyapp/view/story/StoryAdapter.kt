package com.dicoding.picodiploma.storyapp.view.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.databinding.ItemStoryBinding
import com.bumptech.glide.Glide

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClick: ((ListStoryItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ListStoryItem) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }
    inner class MyViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem){
            binding.tvName.text = story.name
            binding.tvDescription.text = story.description
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.ivPicture)

            // Menambahkan listener klik ke itemView
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(story)
                }
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}