package com.example.ui.imagelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.ui.imagelist.databinding.DialogFragmentImageInfoBinding
import com.example.ui.viewmodel.ImageInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ImageInfoDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding : DialogFragmentImageInfoBinding
    private val args : ImageInfoDialogFragmentArgs by navArgs()

    private val imageInfoViewModel : ImageInfoViewModel by viewModel{ parametersOf(args.images)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<DialogFragmentImageInfoBinding>(inflater,R.layout.dialog_fragment_image_info,container,false)
            .apply {
                viewModel = imageInfoViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        setUi()
        seButtontClickListener()
        return binding.root
    }

    private fun seButtontClickListener() {
        binding.saveButton.setOnClickListener {
            imageInfoViewModel.saveImage()
            Toast.makeText(requireContext(),R.string.added_to_favorites,Toast.LENGTH_LONG).show()
            this@ImageInfoDialogFragment.dismiss()
        }
        binding.removeButton.setOnClickListener {
            imageInfoViewModel.removeImage()
            Toast.makeText(requireContext(),R.string.removed_from_favorites,Toast.LENGTH_LONG).show()
            this@ImageInfoDialogFragment.dismiss()
        }
    }

    private fun setUi() {
        val image = imageInfoViewModel.image
        with(binding){
            imageSubreddit.text =  getString(R.string.subreddit_name_prefixed, image.username)
            imageTitle.text = image.username
        }
    }

}