package com.example.heysrealprojcet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.heysrealprojcet.databinding.MainFragmentBinding

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding
   private lateinit var customPagerAdapter: CustomPagerAdapter
   private lateinit var imageList: MutableList<Int>
   private var positon: Int = 0

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      imageList = mutableListOf(R.drawable.pic1, R.drawable.pic2, R.drawable.pic3)

      customPagerAdapter = CustomPagerAdapter()
      customPagerAdapter.submitList(imageList)

      binding.viewpager.adapter = customPagerAdapter
      binding.indicator.setViewPager(binding.viewpager)
      binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)

      binding.btnSkip.setOnClickListener {
         // 스킵 버튼 클릭시
         binding.viewpager.currentItem = 2
      }

      binding.nextButton.setOnClickListener {
         // 다음 버튼 누를때마다 viewPager 페이지 바꾸기
         when (positon) {
            0 -> binding.viewpager.currentItem = 1
            1 -> binding.viewpager.currentItem = 2
         }
      }
   }

   private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
      // currentPosition 변수 안에 현재 페이지값 저장
      override fun onPageSelected(position: Int) {
         super.onPageSelected(position)
         positon = position
         Log.i("position: ", positon.toString())
      }
   }
}