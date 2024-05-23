package com.dicoding.picodiploma.storyapp.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.storyapp.data.UserRepository
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.storyapp.data.StoryRepository
class StoryViewModel(private val repository: UserRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    val apiService = ApiConfig.getApiService()
    val storyRepository: StoryRepository = StoryRepository(apiService)
    val story: LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)
}