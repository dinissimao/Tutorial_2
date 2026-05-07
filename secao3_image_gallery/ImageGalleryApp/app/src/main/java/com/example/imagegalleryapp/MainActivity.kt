package com.example.imagegalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.imagegalleryapp.adapter.FavoritesAdapter
import com.example.imagegalleryapp.adapter.ImageAdapter
import com.example.imagegalleryapp.databinding.ActivityMainBinding
import com.example.imagegalleryapp.viewmodel.GalleryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GalleryViewModel
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]

        setupRecyclerViews()
        setupListeners()
        observeViewModel()
    }

    private val detailsLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val id = data?.getStringExtra("EXTRA_IMAGE_ID") ?: return@registerForActivityResult
            val url = data.getStringExtra("EXTRA_IMAGE_URL") ?: return@registerForActivityResult
            val isFavorite = data.getBooleanExtra("EXTRA_IS_FAVORITE", false)

            val currentFavs = viewModel.favorites.value ?: emptyList()
            val wasFavorite = currentFavs.any { it.id == id }

            if (wasFavorite != isFavorite) {
                // Passamos uma imagem temporária apenas para forçar o ViewModel a inverter o seu estado
                viewModel.toggleFavorite(com.example.imagegalleryapp.model.ImageItem(id, url, !isFavorite))
            }
        }
    }

    private fun setupRecyclerViews() {
        // Adaptador principal (grelha)
        imageAdapter = ImageAdapter { imageItem ->
            openDetails(imageItem)
        }
        binding.rvGallery.adapter = imageAdapter

        // Adaptador de favoritos
        favoritesAdapter = FavoritesAdapter { imageItem ->
            openDetails(imageItem)
        }
        binding.rvFavorites.adapter = favoritesAdapter
    }

    private fun openDetails(imageItem: com.example.imagegalleryapp.model.ImageItem) {
        val intent = Intent(this, ImageDetailsActivity::class.java).apply {
            putExtra("EXTRA_IMAGE_ID", imageItem.id)
            putExtra("EXTRA_IMAGE_URL", imageItem.url)
            putExtra("EXTRA_IS_FAVORITE", imageItem.isFavorite)
        }
        detailsLauncher.launch(intent)
    }


    private fun setupListeners() {
        binding.btnRefresh.setOnClickListener {
            viewModel.loadImages()
        }
    }

    private fun observeViewModel() {
        viewModel.images.observe(this) { images ->
            imageAdapter.submitList(images)
            binding.rvGallery.visibility = if (images.isNotEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.favorites.observe(this) { favorites ->
            favoritesAdapter.submitList(favorites)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    }
}