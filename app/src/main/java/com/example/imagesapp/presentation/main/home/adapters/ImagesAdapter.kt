package com.example.imagesapp.presentation.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesapp.databinding.ItemImageBinding
import com.example.imagesapp.presentation.main.home.models.ImagePreviewUiModel

class ImagesAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<ImagePreviewUiModel, ImagesAdapter.ImageViewHolder>(ImagePreviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImagePreviewUiModel) {
            binding.image = image
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onItemClick(image.id)
            }
        }
    }
}