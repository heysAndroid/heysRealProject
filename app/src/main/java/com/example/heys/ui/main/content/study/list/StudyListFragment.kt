package com.example.heys.ui.main.content.study.list

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
import com.example.heys.databinding.StudyListFragmentBinding
import com.example.heys.enums.ChannelType
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.channel.list.filter.ChannelFilterViewModel
import com.example.heys.ui.main.MainActivity
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   val viewModel by viewModels<StudyListViewModel>()
   lateinit var filterViewModel: ChannelFilterViewModel
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

      viewModel.channelList.observe(viewLifecycleOwner) {
         // 리스트 비어있을 때
         if (it.isEmpty()) {
            binding.noListImage.visibility = View.VISIBLE
            binding.flCreateStudy.visibility = View.GONE

         } else {
            binding.noListImage.visibility = View.GONE
            binding.flCreateStudy.visibility = View.VISIBLE
         }
      }

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

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.filterButton.setOnClickListener { goToFilter() }
      binding.llCreateStudy.setOnClickListener { goToCreateStudy() }
      binding.btnCreateStudy.setOnClickListener { goToCreateStudy() }
   }

   private fun getStudyList(includeClosed: Boolean) {
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
         lastRecruitDate,
         purposes = filterViewModel.selectedPurpose.value ?: null,
         online = filterViewModel.selectedForm.value ?: null,
         location = filterViewModel.selectedRegion.value ?: null,
         includeClosed = includeClosed)
         .observe(viewLifecycleOwner) { response ->
            when (response) {
               is NetworkResult.Success -> {
                  viewModel.setStudyList(response.data?.data)
                  studyItemRecyclerViewAdapter = viewModel.channelList.value?.toMutableList()?.let {
                     StudyItemRecyclerViewAdapter(it) { id ->
                        goToDetail(id)
                     }
                  }!!
                  binding.studyList.adapter = studyItemRecyclerViewAdapter
                  binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
               }

               is NetworkResult.Loading -> {
                  Log.w("getStudyList: ", "loading")
               }

               is NetworkResult.Error -> {
                  Log.w("getStudyList: ", response.message.toString())
               }
            }
         }
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_studyFilterFragment)
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