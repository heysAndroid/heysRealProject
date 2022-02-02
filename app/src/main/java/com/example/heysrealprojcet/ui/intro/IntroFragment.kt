package com.example.heysrealprojcet.ui.intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.IntroFragmentBinding

class IntroFragment : Fragment() {
   private lateinit var binding: IntroFragmentBinding
   private lateinit var customPagerAdapter: CustomPagerAdapter
   private lateinit var imageList: MutableList<Int>
   private var positon: Int = 0

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = IntroFragmentBinding.inflate(inflater, container, false)
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

      binding.btnSkip.setOnClickListener { // 스킵 버튼 클릭시
         binding.viewpager.currentItem = 2
      }

      binding.nextButton.setOnClickListener { // 다음 버튼 누를때마다 viewPager 페이지 바꾸기
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

         when (binding.viewpager.currentItem) {
            2 -> {
               binding.text1.text = "성장하는 청춘 스토리"
               binding.text2.text = "같이 만들어가요!"
               binding.nextButton.text = "가입전에 둘러볼래요!"
               binding.guestButton.visibility = View.VISIBLE
            }
            1 -> {
               binding.text1.text = "팀 빌딩부터 소통까지"
               binding.text2.text = "여기서 다-해결해요!"
               binding.nextButton.text = "다음"
               binding.guestButton.visibility = View.INVISIBLE
            }
            else -> {
               binding.text1.text = "커리어 빌딩을 위한"
               binding.text2.text = "관심정보 여기 다-있다!"
            }
         }
      }
   }
}