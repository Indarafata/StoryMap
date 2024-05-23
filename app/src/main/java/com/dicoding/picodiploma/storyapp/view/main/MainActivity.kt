package com.dicoding.picodiploma.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.storyapp.data.GlobalVariables
import com.dicoding.picodiploma.storyapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.storyapp.view.ViewModelFactory
import com.dicoding.picodiploma.storyapp.view.story.StoryActivity
import com.dicoding.picodiploma.storyapp.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
//                GlobalVariables.token = user.token
//                startActivity(Intent(this, StoryActivity::class.java))
//
//                finish()
                runOnUiThread {
//                    GlobalVariables.token = user.token
                    GlobalVariables.token = "Bearer ${user.token}"
                    startActivity(Intent(this, StoryActivity::class.java))
                    finish()
                }
            }
        }
    }
}