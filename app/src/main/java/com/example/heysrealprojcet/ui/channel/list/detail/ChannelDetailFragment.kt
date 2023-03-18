package com.example.heysrealprojcet.ui.channel.list.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDetailFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelDetailFragment : Fragment() {
   private lateinit var binding: ChannelDetailFragmentBinding
   private val viewModel by viewModels<ChannelDetailViewModel>()
   var isExpanded = false
   val args: ChannelDetailFragmentArgs by navArgs()

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
      binding = ChannelDetailFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.vm = viewModel

      getChannelDetail(args.channelId)
      binding.heysAll.setOnClickListener { goToMemberList() }
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
      binding.waitingHeysList.setOnClickListener { goToWaitingHeysList() }
      binding.btnJoin.setOnClickListener { goToMyChannel() }
   }

   private fun goToMemberList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_memberHeysListFragment)
   }

   private fun goToWaitingHeysList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_waitingHeyListFragment)
   }

   private fun goToMyChannel() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_myChannelFragment)
   }

   private fun getChannelDetail(id: Int) {
      viewModel.getChannelDetail("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.i("getDetail: ", "success")
               viewModel.receiveChannelDetail(response.data?.channelDetail)
            }

            is NetworkResult.Error -> {
               Log.w("getDetail: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getDetail: ", "loading")
            }
         }
      }
   }
}