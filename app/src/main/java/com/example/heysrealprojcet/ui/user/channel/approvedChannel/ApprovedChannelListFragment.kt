package com.example.heysrealprojcet.ui.user.channel.approvedChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ApprovedChannelListFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.user.channel.approvedChannel.leave.ChannelLeaveBottomSheet
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApprovedChannelListFragment : Fragment() {
   private lateinit var binding: ApprovedChannelListFragmentBinding
   private lateinit var adapter: ApprovedChannelItemRecyclerViewAdapter
   private val viewModel: ApprovedChannelListViewModel by viewModels()
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ApprovedChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getMyChannel("Approved")

      adapter = ApprovedChannelItemRecyclerViewAdapter {
         val bottomSheet = ChannelLeaveBottomSheet(it)
         bottomSheet.isCancelable = false
         bottomSheet.setOnOKClickListener { getMyChannel("Approved") }
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }
      binding.rvApprovedChannelList.adapter = adapter
      binding.rvApprovedChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      viewModel.approvedChannelList.observe(viewLifecycleOwner) {
         binding.noListImage.isVisible = it.isEmpty()
         adapter.submitList(it)
      }
   }

   private fun getMyChannel(status: String) {
      viewModel.getMyChannel("Bearer ${UserPreference.accessToken}", status).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("getMyChannel: ", response.data?.message.toString())
               response.data?.myChannel?.let { viewModel.setApprovedChannel(it) }
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