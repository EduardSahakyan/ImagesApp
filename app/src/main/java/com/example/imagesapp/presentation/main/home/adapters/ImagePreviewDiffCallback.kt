package com.example.imagesapp.presentation.main.home.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.imagesapp.presentation.main.home.models.ImagePreviewUiModel

class ImagePreviewDiffCallback : DiffUtil.ItemCallback<ImagePreviewUiModel>() {

    override fun areItemsTheSame(oldItem: ImagePreviewUiModel, newItem: ImagePreviewUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImagePreviewUiModel, newItem: ImagePreviewUiModel): Boolean {
        return oldItem == newItem
    }

}