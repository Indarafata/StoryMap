package com.dicoding.picodiploma.storyapp.view.story

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.storyapp.R
import com.dicoding.picodiploma.storyapp.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.storyapp.view.ViewModelFactory
import com.dicoding.picodiploma.storyapp.view.main.MainActivity
import com.dicoding.picodiploma.storyapp.view.map.MapsActivity

class StoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<StoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityStoryBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

         setData()

        binding?.fabAdd?.setOnClickListener {
            val intent = Intent(this@StoryActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        setupView()
    }

    private fun setData(){
        showLoading(false)
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter
        viewModel.story.observe(this, {
            adapter.submitData(lifecycle, it)
        })

        adapter.setOnItemClickListener { story ->
            val intent = Intent(this@StoryActivity, DetailStoryActivity::class.java)
            intent.putExtra("id", story.id)
            startActivity(intent)
            Toast.makeText(this, "Klik pada ${story.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                val intent = Intent(this@StoryActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                return true
            }
            R.id.action_maps -> {
                val intent = Intent(this@StoryActivity, MapsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}