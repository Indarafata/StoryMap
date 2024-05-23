package com.dicoding.picodiploma.storyapp.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.storyapp.data.response.Story
import com.dicoding.picodiploma.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra("id")
        val ViewModel = DetailStoryViewModel(storyId!!)

        ViewModel.detailStory.observe(this) { it ->
            setStoryData(it)
        }

        ViewModel.isLoading.observe(this) { it ->
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setStoryData(story: Story) {
        binding.tvName.text = story.name
        binding.tvDescription.text = story.description
        // Log.d("detail act 3", "Root context: ${this@DetailStoryActivity}")
        Glide.with(this@DetailStoryActivity)
            .load(story.photoUrl)
            .into(binding.imageView)
    }
}