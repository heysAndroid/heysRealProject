package com.example.heysrealprojcet.ui.channel.create.preview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelPreviewFragmentBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.ChannelPreference
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelPreviewFragment() : Fragment() {
   private lateinit var binding: ChannelPreviewFragmentBinding
   private val viewModel by viewModels<ChannelPreviewViewModel>()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPreviewFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      setChannelRegion()
      setChannelRecruitmentMethod()

      binding.btnNext.setOnClickListener { requestCreateStudy() }
      binding.btnBack.setOnClickListener { goToBack() }

      viewModel.link1.observe(viewLifecycleOwner) {
         if (it.contains("kakao")) {
            binding.link1.setImageResource(R.drawable.ic_link_kakao)
         } else {
            binding.link1.setImageResource(R.drawable.ic_link_clip)
         }
      }

      viewModel.link2.observe(viewLifecycleOwner) {
         if (!it.isNullOrBlank()) {
            binding.link2.visibility = View.VISIBLE
            if (it.contains("kakao")) {
               binding.link2.setImageResource(R.drawable.ic_link_kakao)
            } else {
               binding.link2.setImageResource(R.drawable.ic_link_clip)
            }
         }
      }
   }

   private fun goToBack() {
      findNavController().navigate(R.id.action_channelPreviewFragment_to_backButtonPopupFragment)
   }

   // TODO
   // 1) detail 프래그먼트로 이동
   // 2) 생성완료 프래그먼트 생성
   private fun goToDetail() {
      //findNavController().navigate(R.id.action_channelPreviewFragment_to_channelDetailFragment)
   }

   private fun setChannelRegion() {
      //온라인이면 지역 텍스트 숨기기
      if (viewModel.channelForm.value == ChannelForm.Online.form) {
         binding.channelRegion.visibility = View.GONE
      } else {
         binding.channelRegion.visibility = View.VISIBLE
      }
   }

   private fun setChannelRecruitmentMethod() {
      binding.channelRecruitmentMethod.text = if (viewModel.channelRecruitmentMethod.value == ChannelRecruitmentMethod.Approval.method) {
         "승인없이 바로 참여가능해요."
      } else {
         "승인이 필요해요."
      }
   }

   private fun requestCreateStudy() {
      viewModel.channelInterest
      val study = Study(
         name = ChannelPreference.channelName,
         purpose = viewModel.channelPurpose.value,
         online = ChannelPreference.channelFormEng,
         location = ChannelPreference.channelRegion,
         limit = ChannelPreference.channelCapacity,
         recruitEndDate = ChannelPreference.channelRecruitEndDateTime,
         recruitMethod = ChannelPreference.channelRecruitmentMethodEng,
         contentText = ChannelPreference.channelActivity,
         recruitText = ChannelPreference.channelMember,
         thumbnailUri = "",
         linkUri = arrayListOf(viewModel.link1.value!!, viewModel.link2.value!!),
         interests = ChannelPreference.channelInterestArray
      )
      val token = UserPreference.accessToken
      viewModel.createStudy("Bearer $token", study)
      viewModel.responseCreateStudy.observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.i("createStudy: ", "success")
            }

            is NetworkResult.Error -> {
               Log.w("createStudy: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("createStudy: ", "loading")
            }
         }
      }


   }
}