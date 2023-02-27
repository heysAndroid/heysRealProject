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
      if (myPage.introduce.isNullOrBlank()) {
         binding.introduce.text = "아직 소개할 내용이 없어요."
         binding.introduce.setTextColor(resources.getColor(R.color.color_828282))
      } else {
         binding.introduce.text = "\"${myPage.introduce}\""
         binding.introduce.setTextColor(resources.getColor(R.color.color_262626))
      }

      binding.name.text = myPage.name

      var interestString = ""
      myPage.interests.forEach {
         interestString += "# $it"
      }
      binding.interestContent.text = interestString

      if (myPage.job.isNullOrBlank()) {
         binding.jobTitle.setTextColor(resources.getColor(R.color.color_4d828282))
         binding.job.text = "아직 소개할 직업이 없어요."
         binding.job.setTextColor(resources.getColor(R.color.color_4d262626))
      } else {
         binding.jobTitle.setTextColor(resources.getColor(R.color.color_828282))
         binding.job.text = myPage.job
         binding.job.setTextColor(resources.getColor(R.color.color_262626))
      }

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
      binding.joinChannel.text = myPage.joinChannelCount.toString()
      binding.waitingChannel.text = myPage.waitingChannelCount.toString()
   }
}