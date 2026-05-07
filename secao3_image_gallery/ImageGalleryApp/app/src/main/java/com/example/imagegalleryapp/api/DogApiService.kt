package com.example.imagegalleryapp.api

import com.example.imagegalleryapp.model.ApiResponse
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random/20")
    suspend fun getRandomImages(): ApiResponse
}
