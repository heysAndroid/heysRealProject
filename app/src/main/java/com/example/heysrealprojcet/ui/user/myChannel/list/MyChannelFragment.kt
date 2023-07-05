package com.example.heysrealprojcet.ui.user.myChannel.list

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
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChannelFragment : Fragment() {
   private lateinit var binding: MyChannelFragmentBinding
   private lateinit var myChannelItemRecyclerViewAdapter: MyChannelItemRecyclerViewAdapter
   private val viewModel by viewModels<MyChannelViewModel>()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      getMyChannelList()
   }

   private fun getMyChannelList() {
      viewModel.getMyChannelList("Bearer ${UserPreference.accessToken}").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               response.data?.myChannel?.toMutableList()?.let {
                  myChannelItemRecyclerViewAdapter = MyChannelItemRecyclerViewAdapter(it) { channelId ->
                     goToDetail(channelId)
                  }
               }
               binding.myChannelList.adapter = myChannelItemRecyclerViewAdapter
               binding.myChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }

            is NetworkResult.Loading -> {
               Log.w("getmyChannel", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getmyChannel", response.message.toString())
            }
         }
      }
   }

   private fun goToDetail(channelId: Int) {
      findNavController().navigate(R.id.action_myChannelFragment_to_channelDetailFragment, bundleOf("channelId" to channelId))
   }
}