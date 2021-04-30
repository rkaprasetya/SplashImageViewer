package com.example.ui.imagelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.model.ImageModel
import com.example.data.model.Result
import com.example.ui.adapter.ImageAdapter
import com.example.ui.imagelist.databinding.FragmentImageListBinding
import com.example.ui.viewmodel.ImageListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageListFragment : Fragment() {
    private lateinit var binding : FragmentImageListBinding
    private val viewModel : ImageListViewModel by viewModel()
    private val imageAdapter = ImageAdapter(this::onItemClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater,container,false)
        setImagesObserver()
        setImagesRecyclerView()
        setSwipeRefreshLayout()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setImagesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.images.collect { result ->
                when(result){
                    is Result.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    is Result.Success -> {
                        showImageRecyclerView(result.data)
                    }
                    is Result.Empty -> {
                        showEmptyView(getString(R.string.no_image_show))
                    }
                    is Result.Error -> {
                        val message = if (result.isNetworkError){
                            getString(R.string.no_internet)
                        }else{
                            getString(R.string.no_image_show)
                        }
                        showEmptyView(message)
                    }
                }
            }
        }
    }

    private fun showEmptyView(data: String) {
        with(binding) {
            // Stop refreshing state
            swipeRefreshLayout.isRefreshing = false

            imagesRecyclerView.visibility = View.INVISIBLE
            noDataGroup.visibility = View.VISIBLE

            noDataText.text = data
        }

        // Submit an empty list since there is no data
        imageAdapter.submitList(emptyList())
    }

    private fun showImageRecyclerView(data: List<ImageModel>?) {
        with(binding){
            swipeRefreshLayout.isRefreshing = false
            imagesRecyclerView.visibility = View.VISIBLE
            noDataGroup.visibility = View.GONE

        }
        imageAdapter.submitList(data)
    }

    private fun setImagesRecyclerView(){
        binding.imagesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = imageAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_images,menu)
        setSearchView(menu)
    }

    private fun setSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).apply {
            queryHint = getString(R.string.subreddit)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.setQuery(newText)
                    return true
                }
            })
        }
    }
    /**
     * SwipeRefreshLayout configuration.
     *
     * Disable `pull to refresh` gesture and use only progress animation to indicate data loading.
     */
    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.isEnabled = false
    }

    private fun onItemClicked(position : Int) {
        val images = imageAdapter.currentList.toTypedArray()
        val direction = HomeViewPagerFragmentDirections.homeViewPagerFragmentToGalleryFragment(position,images)
        findNavController().navigate(direction)
    }
}