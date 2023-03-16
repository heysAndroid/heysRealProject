package com.example.heysrealprojcet.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.IntroFragmentBinding
import com.example.heysrealprojcet.model.IntroDescription
import com.example.heysrealprojcet.ui.main.MainActivity

class IntroFragment : Fragment() {
   private lateinit var binding: IntroFragmentBinding
   private lateinit var customPagerAdapter: CustomPagerAdapter
   private lateinit var imageList: MutableList<IntroDescription>
   private var positon: Int = 0

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
      binding = IntroFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setImageList()
      customPagerAdapter = CustomPagerAdapter()
      customPagerAdapter.submitList(imageList)

      binding.viewpager.adapter = customPagerAdapter
      binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
      binding.indicator.setViewPager(binding.viewpager)

      binding.skipButton.setOnClickListener { binding.viewpager.currentItem = 2 }
      binding.loginButton.setOnClickListener { goToLogin() }
      binding.signUpButton.setOnClickListener {
         IntroSignUpBottomSheet {
            goToSignUp()
         }.show(childFragmentManager, null)
      }

      // 다음 버튼 누를때마다 viewPager 페이지 바꾸기
      binding.nextButton.setOnClickListener {
         when (positon) {
            0 -> binding.viewpager.currentItem = 1
            1 -> binding.viewpager.currentItem = 2
         }
      }
   }

   private fun setImageList() {
      imageList = mutableListOf(
         IntroDescription("더 쉽게", "누구나 가능한\n효율적인 모임 활동", ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding1)!!),
         IntroDescription("더 빠르게", "마음 맞는\n팀원들과 함께 성장", ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding2)!!),
         IntroDescription("더 섬세하게", "나만을 위한 관심분야\n맞춤 서비스 제공", ContextCompat.getDrawable(requireContext(), R.drawable.ic_onboarding3)!!),
      )
   }

   private fun goToLogin() {
      val intent = Intent(requireContext(), MainActivity::class.java).apply {
         putExtra(Intent.EXTRA_TEXT, "login")
         type = "text/plain"
      }
      startActivity(intent)
      requireActivity().finish()
   }

   private fun goToSignUp() {
      val intent = Intent(requireContext(), MainActivity::class.java).apply {
         putExtra(Intent.EXTRA_TEXT, "signUp")
         type = "text/plain"
      }
      startActivity(intent)
      requireActivity().finish()
   }

   private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
      // currentPosition 변수 안에 현재 페이지값 저장
      override fun onPageSelected(position: Int) {
         super.onPageSelected(position)
         positon = position

         when (binding.viewpager.currentItem) {
            2 -> {
               binding.nextButton.text = "가입전에 둘러볼래요!"
               binding.loginButton.visibility = View.VISIBLE
               binding.signUpButton.visibility = View.VISIBLE
               binding.nextButton.visibility = View.INVISIBLE
               binding.skipButton.visibility = View.INVISIBLE
            }
            1 -> {
               binding.nextButton.text = "다음"
               binding.loginButton.visibility = View.INVISIBLE
               binding.signUpButton.visibility = View.INVISIBLE
               binding.nextButton.visibility = View.VISIBLE
               binding.skipButton.visibility = View.VISIBLE
            }
         }
      }
   }
}