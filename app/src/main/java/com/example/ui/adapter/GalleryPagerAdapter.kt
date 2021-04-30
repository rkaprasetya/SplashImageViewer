package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.data.model.ImageModel
import com.example.data.model.ListImages
import com.example.ui.imagelist.databinding.ListItemGalleryBinding

class GalleryPagerAdapter(private val onItemClicked: (ImageModel) -> Unit,
                          private val images:MutableList<ImageModel> = mutableListOf()):
RecyclerView.Adapter<GalleryPagerAdapter.ViewHolder>(){


    class ViewHolder(private val binding:ListItemGalleryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(image:ImageModel,onItemClicked:(ImageModel)->Unit){
            binding.imageView.setOnClickListener {
                onItemClicked(image)
            }
            Glide.with(binding.root)
                .load(image.urlPhoto)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position],onItemClicked)
    }
    fun submitList(list: ListImages) {
        if (list != null){
            this.images.clear()
            this.images.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = images.size
}