package com.dicoding.picodiploma.storyapp.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.storyapp.data.GlobalVariables
import com.dicoding.picodiploma.storyapp.data.UserRepository
import com.dicoding.picodiploma.storyapp.data.pref.UserModel
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.response.StoryResponse
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
//retrofit
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}