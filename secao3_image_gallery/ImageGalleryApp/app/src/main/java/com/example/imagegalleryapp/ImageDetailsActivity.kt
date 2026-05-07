package com.example.imagegalleryapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagegalleryapp.databinding.ActivityImageDetailsBinding

class ImageDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailsBinding
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageId = intent.getStringExtra("EXTRA_IMAGE_ID") ?: ""
        val imageUrl = intent.getStringExtra("EXTRA_IMAGE_URL") ?: ""
        isFavorite = intent.getBooleanExtra("EXTRA_IS_FAVORITE", false)

        binding.tvImageId.text = "ID: $imageId"
        binding.tvImageUrl.text = imageUrl

        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(binding.ivBigDog)

        updateFavoriteIcon()

        // Fechar o ecrã de detalhes ao clicar no botão "Voltar" da Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Lidar com o clique no botão de Favorito
        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon()

            // Devolver o resultado à MainActivity (para atualizar o ViewModel e as grelhas)
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_IMAGE_ID", imageId)
            resultIntent.putExtra("EXTRA_IMAGE_URL", imageUrl)
            resultIntent.putExtra("EXTRA_IS_FAVORITE", isFavorite)
            setResult(RESULT_OK, resultIntent)
        }
    }

    private fun updateFavoriteIcon() {
        val iconRes = if (isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        binding.fabFavorite.setImageResource(iconRes)
    }
}
