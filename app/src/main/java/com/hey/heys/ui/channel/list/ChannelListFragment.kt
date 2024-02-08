package com.hey.heys.ui.channel.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

@AndroidEntryPoint
class ChannelListFragment : Fragment() {
   private lateinit var binding: ChannelListFragmentBinding
   private lateinit var channelItemRecyclerViewAdapter: ChannelItemRecyclerViewAdapter
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

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      when (args.channelType) {
         ChannelType.Contest.typeEng -> {
            binding.tvTitle.text = "공모전 채널"
         }

         ChannelType.Extracurricular.typeEng -> {
            binding.tvTitle.text = "대외활동 채널"
         }
      }

      getContentChannelList()
      viewModel.channelList.observe(viewLifecycleOwner) {
         if (it.isEmpty()) {
            binding.flCreateChannel.visibility = View.GONE
            binding.noListImage.visibility = View.VISIBLE
            binding.btnChannel.setOnClickListener { goToCreateChannel() }
         } else {
            binding.noListImage.visibility = View.GONE
            binding.flCreateChannel.visibility = View.VISIBLE
            binding.imgCreateChannel.setOnClickListener { goToCreateChannel() }
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
      val token = UserPreference.accessToken
      viewModel.getContentChannel("Bearer $token", args.contentId, null, null, null, null, null).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setContentChannelList(response.data?.data)
               channelItemRecyclerViewAdapter = viewModel.channelList.value?.toMutableList()?.let {
                  ChannelItemRecyclerViewAdapter(it) { channelId ->
                     goToChannelDetail(channelId)
                  }
               }!!
               binding.channelList.adapter = channelItemRecyclerViewAdapter
               binding.channelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }

            is NetworkResult.Loading -> {
               Log.w("getContentChannelList: ", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getContentChannelList: ", response.message.toString())
            }
         }
      }
   }
}