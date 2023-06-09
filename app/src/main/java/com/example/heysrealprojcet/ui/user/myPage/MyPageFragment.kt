package com.example.heysrealprojcet.ui.user.myPage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyPageFragmentBinding
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.model.network.MyPage
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
   private lateinit var binding: MyPageFragmentBinding
   private val viewModel: MyPageViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = MyPageFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getMyInfo()

      with(binding) {
         goToSetting.setOnClickListener { goToSetting() }
         editProfile.setOnClickListener { goToProfileEdit() }
         engagedChannelContainer.setOnClickListener { goToEngagedChannel() }
         waitingChannelContainer.setOnClickListener { goToWaitingChannel() }
         bookmarkChannel.setOnClickListener { goToBookmarkCollection() }
      }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_myPageFragment_to_settingFragment)
   }

   private fun goToEngagedChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_engagedChannelListFragment)
   }

   private fun goToWaitingChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_waitingChannelListFragment)
   }

   private fun goToProfileEdit() {
      findNavController().navigate(R.id.action_myPageFragment_to_profileEditFragment)
   }

   private fun goToBookmarkCollection() {
      findNavController().navigate(R.id.action_myPageFragment_to_bookmarkListFragment)
   }

   private fun getMyInfo() {
      val token = UserPreference.accessToken
      viewModel.getMyInfo("Bearer $token")
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               response.data?.user?.let { setMyPageInfo(it) }
            }

            is NetworkResult.Error -> {
               alert.setTitle("마이페이지 로딩 실패").setMessage("마이페이지 로딩에 실패했습니다.").create().show()
            }

            is NetworkResult.Loading -> {
               alert.setTitle("마이페이지 로딩 중").setMessage("마이페이지 로딩이 지연되고 있습니다.").create().show()
            }
         }
      }
   }

   private fun setMyPageInfo(myPage: MyPage) {
      with(binding) {
         // 자기소개
         if (myPage.introduce.isNullOrBlank()) {
            introduce.text = "아직 소개할 내용이 없어요."
            introduce.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
         } else {
            introduce.text = "\"${myPage.introduce}\""
            introduce.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
         }

         // 이름
         name.text = myPage.name

         // mbti
         if (myPage.userPersonality.isNullOrBlank()) {
            mbti.text = "????"
            mbti.isEnabled = false
         } else {
            mbti.text = myPage.userPersonality
            mbti.isEnabled = true
         }

         // 관심분야/직무
         var interestString = ""
         myPage.interests.forEach { interestString += "#$it " }
         interestContent.text = interestString

         // 직업
         if (myPage.job.isNullOrBlank()) {

            jobTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d828282))
            job.apply {
               text = "아직 소개할 직업이 없어요."
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d262626))
            }

         } else {

            jobTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
            job.apply {
               text = myPage.job
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            }

         }

         // 사용가능한 스킬
         if (myPage.capability.isNullOrBlank()) {
            skillTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d828282))
            skill.apply {
               text = "아직 소개할 스킬이 없어요."
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d262626))
            }

         } else {
            skillTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
            var skillString = ""
            myPage.capability.forEach {
               skillString += "#$it "
            }
            skill.apply {
               text = skillString
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            }
         }
         setByPercent(myPage.percentage, myPage.gender)

         joinChannel.text = "${myPage.joinChannelCount}"
         waitingChannel.text = "${myPage.waitingChannelCount}"
      }
   }

   private fun setByPercent(percentage: Int, gender: String) {
      // 프로필 이미지 설정
      when (percentage) {
         in 0..49 -> {
            binding.editText.text = "프로필이 채워지지 않았어요"
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_0)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_0)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_0)
            }
         }

         in 50..99 -> {
            binding.editText.text = "프로필을 더 채워주세요."
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_50)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_50)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_50)
            }
         }

         100 -> {
            binding.editText.text = "완성된 프로필 카드를 만들었어요."
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_100)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_100)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_100)
            }
         }
      }
   }
}