package com.example.imagegalleryapp.repository

import com.example.imagegalleryapp.api.DogApiService
import com.example.imagegalleryapp.api.RetrofitInstance
import com.example.imagegalleryapp.model.ImageItem
import java.util.UUID

class ImageRepository {
    private val api: DogApiService = RetrofitInstance.api
    
    // Cache em memória
    private val cachedImages = mutableListOf<ImageItem>()
    private val MAX_CACHE_SIZE = 50

    suspend fun getImages(): Result<List<ImageItem>> {
        return try {
            val response = api.getRandomImages()
            if (response.status == "success") {
                val newImages = response.message.map { url ->
                    ImageItem(
                        id = UUID.randomUUID().toString(),
                        url = url
                    )
                }
                updateCache(newImages)
                Result.success(newImages)
            } else {
                handleOfflineFallback(Exception("API retornou status: ${response.status}"))
            }
        } catch (e: Exception) {
            handleOfflineFallback(e)
        }
    }

    private fun handleOfflineFallback(e: Exception): Result<List<ImageItem>> {
        return if (cachedImages.isNotEmpty()) {
            // Em caso de erro (ex: offline), devolve a cache se disponível
            Result.success(cachedImages.toList())
        } else {
            Result.failure(e)
        }
    }

    private fun updateCache(newImages: List<ImageItem>) {
        // Adiciona as novas imagens no início da cache
        cachedImages.addAll(0, newImages)
        
        // Mantém apenas o limite de MAX_CACHE_SIZE imagens
        if (cachedImages.size > MAX_CACHE_SIZE) {
            val excess = cachedImages.size - MAX_CACHE_SIZE
            for (i in 0 until excess) {
                cachedImages.removeAt(cachedImages.size - 1)
            }
        }
    }
}
