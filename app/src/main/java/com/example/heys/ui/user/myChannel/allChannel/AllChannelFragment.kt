package com.example.heys.ui.user.myChannel.allChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.R
import com.example.heys.databinding.AllChannelFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.channel.list.ChannelItemRecyclerViewAdapter
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllChannelFragment : Fragment() {
   private lateinit var binding: AllChannelFragmentBinding
   private lateinit var channelItemRecyclerViewAdapter: ChannelItemRecyclerViewAdapter
   private val viewModel by viewModels<AllChannelViewModel>()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = AllChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      getAllChannelList()
   }

   private fun getAllChannelList() {
      val token = UserPreference.accessToken
      viewModel.getAllChannel("Bearer $token", null, null, null, null, null).observe(viewLifecycleOwner) { response ->
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

   private fun goToChannelDetail(channelId: Int) {
      findNavController().navigate(R.id.action_myChannelFragment_to_channelDetailFragment, bundleOf("channelId" to channelId))
   }
}