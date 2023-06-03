package com.example.heysrealprojcet.ui.channel.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelListFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
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

      when (args.channelType) {
         "contest" -> {
            binding.tvTitle.text = "공모전 채널"
            binding.layoutFilter.visibility = View.GONE
         }

         "extra" -> {
            binding.tvTitle.text = "대외활동 채널"
            binding.layoutFilter.visibility = View.GONE
         }

         else -> {
            binding.tvTitle.text = "스터디 채널"
            binding.layoutFilter.visibility = View.VISIBLE
         }
      }

      binding.btnFilter.setOnClickListener { goToFilter() }
      getContentChannelList()

      viewModel.channelList.observe(viewLifecycleOwner) {
         if (it.isEmpty()) {
            binding.nsCreateChnnel.visibility = View.GONE
            binding.noListImage.visibility = View.VISIBLE
            binding.btnChannel.setOnClickListener { goToCreateChannel() }
         } else {
            binding.noListImage.visibility = View.GONE
            binding.nsCreateChnnel.visibility = View.VISIBLE
            binding.imgCreateChannel.setOnClickListener { goToCreateChannel() }
         }
      }
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_channelListFragment_to_channelFilterFragment)
   }

   private fun goToCreateChannel() {
      findNavController().navigate(R.id.action_channelListFragment_to_channelNameFragment)
   }

   private fun goToChannelDetail() {
      findNavController().navigate(R.id.action_channelListFragment_to_channelDetailFragment)
   }

   private fun getContentChannelList() {
      val token = UserPreference.accessToken
      viewModel.getContentChannel("Bearer $token", args.contentId, null, null, null, null, null).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setContentChannelList(response.data?.data)
               channelItemRecyclerViewAdapter = viewModel.channelList.value?.toMutableList()?.let { ChannelItemRecyclerViewAdapter(it) { goToChannelDetail() } }!!
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