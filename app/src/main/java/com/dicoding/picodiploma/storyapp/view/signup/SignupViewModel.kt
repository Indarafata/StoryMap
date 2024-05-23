package com.dicoding.picodiploma.storyapp.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.storyapp.data.pref.UserModel
import com.dicoding.picodiploma.storyapp.data.response.RegisterResponse
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel() : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun postRegister(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        _isLoading.value = true

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _isLoading.value = false
                    _isSuccess.value = true
                } else {
                    _isLoading.value = false
                    _isSuccess.value = true
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = true
            }
        })
    }
}