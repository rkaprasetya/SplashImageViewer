package com.example.ui.imagelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.model.ImageModel
import com.example.ui.adapter.GalleryPagerAdapter
import com.example.ui.imagelist.databinding.FragmentGalleryBinding
class GalleryFragment : Fragment() {

    private lateinit var binding : FragmentGalleryBinding
    private val args: GalleryFragmentArgs by navArgs()

    private val galleryAdapter = GalleryPagerAdapter(this::onItemClicked)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater,container,false)
        setViewPager()
        setFullscreen()
        return binding.root
    }

    private fun setViewPager() {
        binding.viewPager.adapter = galleryAdapter

        val images = args.images.toList()
        galleryAdapter.submitList(images)
        val position = args.position
        binding.viewPager.setCurrentItem(position,false)
    }



    private fun onItemClicked(image: ImageModel) {
        // Get direction and pass the image
        val direction = GalleryFragmentDirections.galleryFragmentToImageInfoDialogFragment(image)

        // Navigate to ImageInfoDialogFragment
        findNavController().navigate(direction)
    }

    override fun onDestroy() {
        super.onDestroy()
        exitFullscreen()
    }

    /**
     * Enable fullscreen mode.
     */
    private fun setFullscreen() {
        activity?.window?.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    /**
     * Disable fullscreen mode.
     */
    private fun exitFullscreen() {
        activity?.window?.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}


