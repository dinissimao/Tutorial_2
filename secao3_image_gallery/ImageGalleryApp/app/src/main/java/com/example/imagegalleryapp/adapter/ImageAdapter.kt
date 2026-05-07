package com.example.imagegalleryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegalleryapp.databinding.ItemImageBinding
import com.example.imagegalleryapp.model.ImageItem

class ImageAdapter(
    private val onClick: (ImageItem) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val items = mutableListOf<ImageItem>()

    fun submitList(newItems: List<ImageItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageItem) {
            // Carregar imagem usando o Glide
            Glide.with(binding.root.context)
                .load(item.url)
                .centerCrop()
                .into(binding.ivDog)

            // Mostrar/Esconder a indicação de favorito com base no estado do item
            if (item.isFavorite) {
                binding.ivFavorite.visibility = View.VISIBLE
            } else {
                binding.ivFavorite.visibility = View.GONE
            }

            // Detectar o clique num cão e enviar para o onClick para abrir detalhes
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}
