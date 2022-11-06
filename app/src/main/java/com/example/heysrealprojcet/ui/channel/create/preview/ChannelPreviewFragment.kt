package com.example.heysrealprojcet.ui.channel.create.preview

import android.os.Bundle
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
import com.example.heysrealprojcet.ui.main.MainActivity

class ChannelPreviewFragment() : Fragment() {
   private lateinit var binding: ChannelPreviewFragmentBinding
   private val viewModel by viewModels<ChannelPreviewViewModel>()
   var isExpanded = false

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

      binding.btnDetail.setOnClickListener {
         if (isExpanded) {
            binding.channelDescription.maxLines = 5
            binding.btnDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
            isExpanded = false
         } else {
            // TODO
            // close 리소스 수정
            binding.channelDescription.maxLines = Int.MAX_VALUE
            binding.btnDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_close, 0)
            isExpanded = true
         }
      }
      binding.btnMake.setOnClickListener { goToDetail() }
   }

   // TODO
   // 1) detail 프래그먼트로 이동
   // 2) 생성완료 프래그먼트 생성
   private fun goToDetail() {
      findNavController().navigate(R.id.action_channelPreviewFragment_to_channelDetailFragment)
   }

   private fun setChannelRegion() {
      //온라인이면 지역 텍스트 숨기기
      if (viewModel.channelForm.value == ChannelForm.Online.form) {
         binding.channelRegion.visibility = View.GONE
         binding.channelRegionText.visibility = View.GONE
      } else {
         binding.channelRegion.visibility = View.VISIBLE
         binding.channelRegionText.visibility = View.VISIBLE
      }
   }

   private fun setChannelRecruitmentMethod() {
      binding.channelRecruitmentMethod.text = if (viewModel.channelRecruitmentMethod.value == ChannelRecruitmentMethod.Approval.method) {
         "승인이 필요하지 않은 채널이에요."
      } else {
         "승인이 필요한 채널이에요."
      }
   }
}