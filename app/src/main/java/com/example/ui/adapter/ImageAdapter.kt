package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.data.model.ImageModel
import com.example.ui.imagelist.databinding.ListItemImageBinding

class ImageAdapter(
    private val onItemClicked: (Int) -> Unit
) : ListAdapter<ImageModel,RecyclerView.ViewHolder>(ImageDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = getItem(position)
        (holder as ImageViewHolder).bind(image, onItemClicked)
    }

    class ImageViewHolder(private val binding : ListItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(image : ImageModel, onItemClicked: (Int) -> Unit){
            binding.root.setOnClickListener {
                onItemClicked(adapterPosition)
            }
            Glide.with(binding.root)
                .load(image.urlPhoto)
                .thumbnail(0.25f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)
        }
    }
    fun ImageView.loadWithThumbnail(uri: String?, sizeMultiplier: Float = 0.25f) {
        Glide.with(context)
            .load(uri)
            .thumbnail(sizeMultiplier)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }

    private class ImageDiffCallback : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel) =
            oldItem == newItem

    }
}