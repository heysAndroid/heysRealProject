package com.hey.heys.ui.channel.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.R
import com.hey.heys.databinding.ChannelListFragmentBinding
import com.hey.heys.enums.ChannelType
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChannelListFragment : Fragment() {
   private lateinit var binding: ChannelListFragmentBinding
   private lateinit var adapter: ChannelItemRecyclerViewAdapter
   val viewModel by viewModels<ChannelListViewModel>()
   val args: ChannelListFragmentArgs by navArgs()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onPause() {
      super.onPause()
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
      binding = ChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      adapter = ChannelItemRecyclerViewAdapter()
      binding.channelList.adapter = adapter
      binding.channelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter.onClickListener = { goToChannelDetail(it) }

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnChannel.setOnClickListener { goToCreateChannel() }
      binding.imgCreateChannel.setOnClickListener { goToCreateChannel() }

      when (args.channelType) {
         ChannelType.Contest.typeEng -> {
            binding.tvTitle.text = "공모전 채널"
         }

         ChannelType.Extracurricular.typeEng -> {
            binding.tvTitle.text = "대외활동 채널"
         }
      }

      getContentChannelList()

      lifecycleScope.launch {
         adapter.loadStateFlow.distinctUntilChangedBy {
            it.refresh
         }.collectLatest {
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
            binding.flCreateChannel.isVisible = !adapter.snapshot().items.isNullOrEmpty()
         }
      }
   }

   private fun goToCreateChannel() {
      findNavController().navigate(
         R.id.action_channelListFragment_to_channelNameFragment,
         bundleOf("channelType" to args.channelType, "contentId" to args.contentId))
   }

   private fun goToChannelDetail(channelId: Int) {
      findNavController().navigate(
         R.id.action_channelListFragment_to_channelDetailFragment,
         bundleOf("channelId" to channelId))
   }

   private fun getContentChannelList() {
      lifecycleScope.launch {
         val token = UserPreference.accessToken
         viewModel.getChannel("Bearer $token", args.contentId, null, null, null, null, null).collect { pagingData ->
            adapter.submitData(pagingData)
         }
      }
   }
}