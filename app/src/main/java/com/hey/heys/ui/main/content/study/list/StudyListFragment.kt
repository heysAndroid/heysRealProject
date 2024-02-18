package com.hey.heys.ui.main.content.study.list

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
import com.hey.heys.databinding.StudyListFragmentBinding
import com.hey.heys.enums.ChannelType
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.channel.list.filter.ChannelFilterViewModel
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var adapter: StudyItemRecyclerViewAdapter
   val viewModel by viewModels<StudyListViewModel>()
   private lateinit var filterViewModel: ChannelFilterViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = StudyListFragmentBinding.inflate(inflater, container, false)
      filterViewModel = ViewModelProvider(requireActivity())[ChannelFilterViewModel::class.java]
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      adapter = StudyItemRecyclerViewAdapter()
      binding.studyList.adapter = adapter
      binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter.onClickListener = { goToDetail(it) }

      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getStudyList(!it)
      }

      filterViewModel.selectedInterest.observe(viewLifecycleOwner) {
         if (it.isNullOrEmpty()) {
            binding.tvFilterCount.text = "0"
         } else {
            binding.tvFilterCount.text = "${it.size}"
         }
      }

      lifecycleScope.launch {
         adapter.loadStateFlow.distinctUntilChangedBy {
            it.refresh
         }.collectLatest {
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
            binding.flCreateStudy.isVisible = !adapter.snapshot().items.isNullOrEmpty()
         }
      }

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.filterButton.setOnClickListener { goToFilter() }
      binding.llCreateStudy.setOnClickListener { goToCreateStudy() }
      binding.btnCreateStudy.setOnClickListener { goToCreateStudy() }
   }

   private fun getStudyList(includeClosed: Boolean) {
      lifecycleScope.launch {
         val token = UserPreference.accessToken
         var lastRecruitDate: String? = null

         filterViewModel.selectedDate?.let {
            val selectedDateTime = LocalDateTime.of(it.year, it.month, it.dayOfMonth, 23, 59, 59)
            val st = selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            lastRecruitDate = st
         }

         viewModel.getStudyList(
            "Bearer $token",
            interest = filterViewModel.selectedInterest.value ?: null,
            lastRecruitDate = lastRecruitDate,
            purposes = filterViewModel.selectedPurpose.value ?: null,
            online = filterViewModel.selectedForm.value ?: null,
            location = filterViewModel.selectedRegion.value ?: null,
            includeClosed = includeClosed).collect { pagingData ->
            adapter.submitData(pagingData)
         }
      }
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_channelFilterFragment)
   }

   private fun goToDetail(channelId: Int) {
      findNavController().navigate(
         R.id.action_studyFragment_to_channelDetailFragment,
         bundleOf("channelId" to channelId))
   }

   private fun goToCreateStudy() {
      findNavController().navigate(
         R.id.action_studyFragment_to_channelNameFragment,
         bundleOf("channelType" to ChannelType.Study.typeEng)
      )
   }
}