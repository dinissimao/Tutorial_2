package com.example.imagegalleryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegalleryapp.model.ImageItem
import com.example.imagegalleryapp.repository.ImageRepository
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private val repository = ImageRepository()

    private val _images = MutableLiveData<List<ImageItem>>(emptyList())
    val images: LiveData<List<ImageItem>> = _images

    private val _favorites = MutableLiveData<List<ImageItem>>(emptyList())
    val favorites: LiveData<List<ImageItem>> = _favorites

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadImages()
    }

    fun loadImages() {
        _isLoading.value = true
        _errorMessage.value = null
        
        viewModelScope.launch {
            val result = repository.getImages()
            
            result.onSuccess { fetchedImages ->
                // Preserva o estado "isFavorite" se a imagem em cache for retornada
                val currentFavIds = _favorites.value?.map { it.id } ?: emptyList()
                val updatedImages = fetchedImages.map { 
                    it.copy(isFavorite = currentFavIds.contains(it.id))
                }
                _images.value = updatedImages
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Ocorreu um erro desconhecido."
            }
            
            _isLoading.value = false
        }
    }

    fun toggleFavorite(imageItem: ImageItem) {
        val currentFavs = _favorites.value?.toMutableList() ?: mutableListOf()
        val isAlreadyFav = currentFavs.any { it.id == imageItem.id }

        if (isAlreadyFav) {
            // Remover dos favoritos
            currentFavs.removeAll { it.id == imageItem.id }
        } else {
            // Lógica de favoritos FIFO (máximo 5)
            if (currentFavs.size >= 5) {
                currentFavs.removeAt(0) // Remove o mais antigo que entrou na lista
            }
            currentFavs.add(imageItem.copy(isFavorite = true))
        }

        _favorites.value = currentFavs

        // Actualizar imediatamente o estado da imagem na grelha principal
        val currentImages = _images.value?.toMutableList() ?: return
        val index = currentImages.indexOfFirst { it.id == imageItem.id }
        if (index != -1) {
            val updatedItem = currentImages[index].copy(isFavorite = !isAlreadyFav)
            currentImages[index] = updatedItem
            _images.value = currentImages
        }
    }
}
