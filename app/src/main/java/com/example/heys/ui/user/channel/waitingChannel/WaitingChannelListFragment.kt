package com.example.heys.ui.user.channel.waitingChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.databinding.WaitingChannelListFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.user.channel.waitingChannel.cancel.ChannelCancelBottomSheet
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingChannelListFragment : Fragment() {
   private lateinit var binding: WaitingChannelListFragmentBinding
   private lateinit var adapter: WaitingChannelItemRecyclerViewAdapter
   private val viewModel: WaitingChannelListViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = WaitingChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getMyChannel("Waiting")

      adapter = WaitingChannelItemRecyclerViewAdapter {
         val bottomSheet = ChannelCancelBottomSheet(it)
         bottomSheet.isCancelable = false
         bottomSheet.setOnOKClickListener { getMyChannel("Waiting") }
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.rvWaitingChannelList.adapter = adapter
      binding.rvWaitingChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      viewModel.waitingChannelList.observe(viewLifecycleOwner) {
         binding.noListImage.isVisible = it.isEmpty()
         adapter.submitList(it)
      }
   }

   private fun getMyChannel(status: String) {
      viewModel.getMyChannel("Bearer ${UserPreference.accessToken}", status).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("getMyChannel: ", response.data?.message.toString())
               response.data?.myChannel?.let { viewModel.setWaitingChannel(it) }
            }

            is NetworkResult.Error -> {
               Log.w("getMyChannel: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getMyChannel: ", "loading")
            }
         }
      }
   }
}