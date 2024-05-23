package com.dicoding.picodiploma.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiConfig
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiService

class StoryRepository(private val apiService: ApiService) {
    fun getStories(): LiveData<PagingData<ListStoryItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}