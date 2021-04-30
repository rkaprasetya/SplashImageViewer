package com.example.ui.imagelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.model.ImageModel
import com.example.data.model.Result
import com.example.ui.adapter.ImageAdapter
import com.example.ui.imagelist.databinding.FragmentCollectionBinding
import com.example.ui.viewmodel.CollectionViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CollectionFragment : Fragment() {
    private lateinit var binding : FragmentCollectionBinding
    private val viewModel : CollectionViewModel by viewModel()
    private val favoriteAdapter = ImageAdapter(this::onItemClicked)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionBinding.inflate(inflater,container,false)
        setFavoritesObserver()
        setFavoritesRecyclerView()
        return binding.root
    }

    private fun setFavoritesRecyclerView() {
        binding.favoritesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = favoriteAdapter
        }
    }

    private fun setFavoritesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteImages.collect { result ->
                when(result){
                    is Result.Success -> {
                        showFavoritesRecyclerView(result.data)
                    }

                    is Result.Empty -> {
                        showEmptyView()
                    }
                }
            }
        }
    }

    private fun showEmptyView() {
        with(binding) {
            favoritesRecyclerView.visibility = View.INVISIBLE
            noDataGroup.visibility = View.VISIBLE
        }

        // Submit an empty list since there is no data
        favoriteAdapter.submitList(emptyList())
    }

    private fun showFavoritesRecyclerView(data: List<ImageModel>?) {
        with(binding) {
            favoritesRecyclerView.visibility = View.VISIBLE
            noDataGroup.visibility = View.GONE
        }

        // Submit the list of images
        favoriteAdapter.submitList(data)
    }

    private fun onItemClicked(position : Int){
        val images = favoriteAdapter.currentList.toTypedArray()
        val direction = HomeViewPagerFragmentDirections.homeViewPagerFragmentToGalleryFragment(position,images)
        findNavController().navigate(direction)
    }

}