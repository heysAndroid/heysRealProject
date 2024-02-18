package com.hey.heys.ui.user.myChannel.allChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.R
import com.hey.heys.databinding.AllChannelFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.channel.list.ChannelItemRecyclerViewAdapter
import com.hey.heys.ui.channel.list.filter.ChannelFilterViewModel
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AllChannelFragment : Fragment() {
   private lateinit var binding: AllChannelFragmentBinding
   private lateinit var adapter: ChannelItemRecyclerViewAdapter
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

      adapter = ChannelItemRecyclerViewAdapter()
      binding.channelList.adapter = adapter
      binding.channelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter.onClickListener = { goToChannelDetail(it) }

      binding.filterButton.setOnClickListener { goToFilter() }

      lifecycleScope.launch {
         adapter.loadStateFlow.distinctUntilChangedBy {
            it.refresh
         }.collectLatest {
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
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
      lifecycleScope.launch {
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
            includeClosed = inCludeClosed).collect { pagingData ->
            adapter.submitData(pagingData)
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