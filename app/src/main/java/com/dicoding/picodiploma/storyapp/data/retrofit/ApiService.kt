package com.dicoding.picodiploma.storyapp.data.retrofit

import com.dicoding.picodiploma.storyapp.data.response.DetailStoryResponse
import com.dicoding.picodiploma.storyapp.data.response.LoginResponse
import com.dicoding.picodiploma.storyapp.data.response.PostStoryResponse
import com.dicoding.picodiploma.storyapp.data.response.RegisterResponse
import com.dicoding.picodiploma.storyapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Response<StoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailStoryResponse>

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): PostStoryResponse

    @GET("stories")
    fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): Call<StoryResponse>
}