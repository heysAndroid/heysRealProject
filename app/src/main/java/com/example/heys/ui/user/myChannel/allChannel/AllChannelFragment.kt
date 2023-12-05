package com.example.heys.ui.user.myChannel.allChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.R
import com.example.heys.databinding.AllChannelFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.channel.list.ChannelItemRecyclerViewAdapter
import com.example.heys.ui.channel.list.filter.ChannelFilterViewModel
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AllChannelFragment : Fragment() {
   private lateinit var binding: AllChannelFragmentBinding
   private lateinit var channelItemRecyclerViewAdapter: ChannelItemRecyclerViewAdapter
   private val viewModel by viewModels<AllChannelViewModel>()
   private lateinit var filterViewModel: ChannelFilterViewModel

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = AllChannelFragmentBinding.inflate(inflater, container, false)
      filterViewModel = ViewModelProvider(requireActivity())[ChannelFilterViewModel::class.java]
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      binding.filterButton.setOnClickListener { goToFilter() }
      viewModel.channelList.observe(viewLifecycleOwner) {
         if (it.isEmpty()) {
            binding.noListImage.visibility = View.VISIBLE
         } else {
            binding.noListImage.visibility = View.GONE
         }
      }

      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getAllChannelList(!it)
      }

      filterViewModel.selectedInterest.observe(viewLifecycleOwner) {
         binding.tvFilterCount.text =
            if (it.isNullOrEmpty()) {
               "0"
            } else {
               "${it.size}"
            }
      }
   }

   private fun getAllChannelList(inCludeClosed: Boolean) {
      val token = UserPreference.accessToken
      var lastRecruitDate: String? = null
      filterViewModel.selectedDate?.let {
         val selectedDateTime = LocalDateTime.of(it.year, it.month, it.dayOfMonth, 23, 59, 59)
         val st = selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
         lastRecruitDate = st
      }

      viewModel.getAllChannel(
         "Bearer $token",
         interest = filterViewModel.selectedInterest.value ?: null,
         lastRecruitDate = lastRecruitDate,
         purposes = filterViewModel.selectedPurpose.value ?: null,
         online = filterViewModel.selectedForm.value ?: null,
         location = filterViewModel.selectedRegion.value ?: null,
         includeClosed = inCludeClosed
      ).observe(viewLifecycleOwner) { response ->
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

   private fun goToFilter() {
      findNavController().navigate(R.id.action_myChannelFragment_to_channelFilterFragment)
   }

   private fun goToChannelDetail(channelId: Int) {
      findNavController().navigate(R.id.action_myChannelFragment_to_channelDetailFragment, bundleOf("channelId" to channelId))
   }
}