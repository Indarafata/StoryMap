package com.dicoding.picodiploma.storyapp.view.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.storyapp.data.GlobalVariables
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.response.StoryResponse
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {
    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story: LiveData<List<ListStoryItem>> = _story

    init {
        getStory()
    }

    private fun getStory() {
        val client = ApiConfig.getApiService().getStoriesWithLocation(GlobalVariables.token)

        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _story.value = response.body()?.listStory
                    }
                } else {
                    // Log.e("StoryViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
            }
        })
    }
}