package com.dicoding.picodiploma.storyapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.storyapp.data.response.ListStoryItem
import com.dicoding.picodiploma.storyapp.data.retrofit.ApiService

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getStories(GlobalVariables.token, page, params.loadSize)

//            if (response.isSuccessful) {
            val stories = response.body()?.listStory

            Log.d("StoryPagingSource", "Data loaded: $stories")

            LoadResult.Page(
                data = stories!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (stories.isNullOrEmpty()) null else page + 1
            )
//            } else {
//                Log.e("StoryPagingSource", "Error loading stories: ${response.message()}")
//                LoadResult.Error(Exception("Failed to load stories"))
//            }
        } catch (e: Exception) {
            Log.e("StoryPagingSource", "Error loading stories", e)
//            LoadResult.Error(e)
            return LoadResult.Error(e)
        }
    }
}
