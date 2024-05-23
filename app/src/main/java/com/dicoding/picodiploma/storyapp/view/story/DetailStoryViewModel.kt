package com.dicoding.picodiploma.storyapp.view.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.storyapp.data.GlobalVariables
import com.dicoding.picodiploma.storyapp.data.response.DetailStoryResponse
import com.dicoding.picodiploma.storyapp.data.response.Story
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(private val id: String) : ViewModel() {

    private val _detailStory = MutableLiveData<Story>()
    val detailStory: LiveData<Story> = _detailStory
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailStoryViewModel"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStory(GlobalVariables.token, id)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
//                showLoading(false)
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailStory.value = response.body()?.story!!
                    }
                } else {
                    // Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
                // Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}