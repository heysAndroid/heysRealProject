package com.hey.heys.ui.channel.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hey.heys.databinding.ChannelEditFragmentBinding
import com.hey.heys.ui.channel.edit.editDialog.EditChannelPurposeDialog
import com.hey.heys.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelEditFragment : Fragment() {
   private lateinit var binding: ChannelEditFragmentBinding
   private val viewModel by viewModels<ChannelEditViewModel>()
   val args: ChannelEditFragmentArgs by navArgs()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ChannelEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      initView()

      binding.tvEditPurpose.setOnClickListener {
         val editPurposeDialog = EditChannelPurposeDialog()
         editPurposeDialog?.setOnOKClickListener { content ->
            binding.channelPurpose.text = content
         }
         editPurposeDialog?.show(childFragmentManager, null)
      }
   }

   private fun initView() {
      viewModel.setRecruitEndDate(args.recruitEndDate)
      viewModel.setRecruitEndTime(args.recruitEndTime)
      viewModel.setInterestString(args.interestString)
      viewModel.setPurposeString(args.purposeString)
      viewModel.setChannelDetail(args.channelDetail)
      viewModel.setTitle(args.channelDetail.title)
      viewModel.setContentText(args.channelDetail.contentText)
      viewModel.setRecruitText(args.channelDetail.recruitText)
      viewModel.setContentTextCount(args.channelDetail.contentText.length)
      viewModel.setRecruitTextCount(args.channelDetail.recruitText.length)
   }
}