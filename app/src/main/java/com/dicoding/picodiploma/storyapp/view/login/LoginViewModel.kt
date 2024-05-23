package com.dicoding.picodiploma.storyapp.view.login

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.storyapp.data.UserRepository
import com.dicoding.picodiploma.storyapp.data.pref.UserModel
import com.dicoding.picodiploma.storyapp.data.response.LoginResponse
import com.dicoding.picodiploma.storyapp.data.response.LoginResult
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import com.dicoding.picodiploma.storyapp.view.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.viewModels

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        // Log.d("sini", user.email)
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}