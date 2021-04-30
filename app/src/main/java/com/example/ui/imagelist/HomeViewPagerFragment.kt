package com.example.ui.imagelist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.adapter.COLLECTION_PAGE_INDEX
import com.example.ui.adapter.HomePagerAdapter
import com.example.ui.adapter.IMAGE_LIST_PAGE_INDEX
import com.example.ui.imagelist.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.bind
import java.lang.IndexOutOfBoundsException

class HomeViewPagerFragment : Fragment() {
    private lateinit var binding : FragmentHomeViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeViewPagerBinding.inflate(inflater,container,false)
        setViewPager()
        setTabLayout()
        setToolbar()
        return binding.root
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun setTabLayout() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        // Set TabLayout icons and texts and attach it to ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
    }
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            IMAGE_LIST_PAGE_INDEX -> R.drawable.images_tab_selector
            COLLECTION_PAGE_INDEX -> R.drawable.collection_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }
    private fun getTabTitle(position: Int): String {
        return when (position) {
            IMAGE_LIST_PAGE_INDEX -> getString(R.string.images)
            COLLECTION_PAGE_INDEX -> getString(R.string.my_collection)
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun setViewPager() {
        binding.viewPager.adapter = HomePagerAdapter(this)
    }

}