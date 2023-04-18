package com.example.heysrealprojcet.ui.user.myPage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyPageFragmentBinding
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.model.MyPage
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
      findNavController().navigate(R.id.action_myPageFragment_to_bookmarkCollectionFragment)
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
      // 자기소개
      if (myPage.introduce.isNullOrBlank()) {
         binding.introduce.text = "아직 소개할 내용이 없어요."
         binding.introduce.setTextColor(resources.getColor(R.color.color_828282))
      } else {
         binding.introduce.text = "\"${myPage.introduce}\""
         binding.introduce.setTextColor(resources.getColor(R.color.color_262626))
      }

      // 이름
      binding.name.text = myPage.name

      // mbti
      if (myPage.userPersonality.isNullOrBlank()) {
         binding.mbti.text = "????"
         binding.mbti.isEnabled = false
      } else {
         binding.mbti.text = myPage.userPersonality
         binding.mbti.isEnabled = true
      }

      // 관심분야/직무
      var interestString = ""
      myPage.interests.forEach {
         interestString += "#$it "
      }
      binding.interestContent.text = interestString

      // 직업
      if (myPage.job.isNullOrBlank()) {
         binding.jobTitle.setTextColor(resources.getColor(R.color.color_4d828282))
         binding.job.text = "아직 소개할 직업이 없어요."
         binding.job.setTextColor(resources.getColor(R.color.color_4d262626))
      } else {
         binding.jobTitle.setTextColor(resources.getColor(R.color.color_828282))
         binding.job.text = myPage.job
         binding.job.setTextColor(resources.getColor(R.color.color_262626))
      }

      // 사용가능한 스킬
      if (myPage.capability.isNullOrBlank()) {
         binding.skillTitle.setTextColor(resources.getColor(R.color.color_4d828282))
         binding.skill.text = "아직 소개할 스킬이 없어요."
         binding.skill.setTextColor(resources.getColor(R.color.color_4d262626))
      } else {
         binding.skillTitle.setTextColor(resources.getColor(R.color.color_828282))
         var skillString = ""
         myPage.capability.forEach {
            skillString += "#$it "
         }
         binding.skill.text = skillString
         binding.skill.setTextColor(resources.getColor(R.color.color_262626))
      }
      setByPercent(myPage.percentage, myPage.gender)

      // 참여중인 채널 수
      binding.joinChannel.text = myPage.joinChannelCount.toString()
      // 승인대기 채널 수
      binding.waitingChannel.text = myPage.waitingChannelCount.toString()
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