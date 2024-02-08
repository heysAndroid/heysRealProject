package com.hey.heys.ui.main.content.contestExtracurricular.extracurricular

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.R
import com.hey.heys.databinding.ExtracurricularListFragmentBinding
import com.hey.heys.enums.ChannelType
import com.hey.heys.enums.ContentOrder
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.ui.main.MainFragment
import com.hey.heys.ui.main.content.contestExtracurricular.contest.ContestAdapter
import com.hey.heys.ui.main.content.contestExtracurricular.filter.ContentsFilterViewModel
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ExtracurricularListFragment : Fragment() {
   private lateinit var binding: ExtracurricularListFragmentBinding
   private val viewModel: ExtracurricularListViewModel by viewModels()
   private lateinit var adapter: ContestAdapter

   lateinit var filterViewModel: ContentsFilterViewModel
   private lateinit var interestList: ArrayList<String>
   private var orderType = "Default"
   private val args: ExtracurricularListFragmentArgs by navArgs()

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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ExtracurricularListFragmentBinding.inflate(inflater, container, false)
      filterViewModel = ViewModelProvider(requireActivity())[ContentsFilterViewModel::class.java]
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      filterViewModel.selectedInterest.value?.forEach {
         Log.w("filters: ", it)
      }
      setInterestAndType()

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.filterCount.text = "${interestList.size}"
      binding.filterButton.setOnClickListener { goToFilter() }

      adapter = ContestAdapter()
      binding.extracurricularList.adapter = adapter
      binding.extracurricularList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter.onClickListener = {
         goToDetail(it)
      }

      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getExtracurricularList(!it)
      }

      lifecycleScope.launch {
         adapter.loadStateFlow.distinctUntilChangedBy {
            it.refresh
         }.collectLatest {
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
         }
      }
   }

   private fun setInterestAndType() {
      interestList = arrayListOf()
      orderType = when (args.type) {
         "interest" -> {
            UserPreference.interests.split(",").forEach { interestList.add(it) }
            ContentOrder.Default.order
         }

         else -> {
            filterViewModel.selectedInterest.value?.forEach {
               interestList.add(it)
            }
            args.type
         }
      }
   }

   private fun goToFilter() {
      when (args.type) {
         "interest" -> {
            findNavController().navigate(
               R.id.action_extracurricularListFragment_to_contentsFilterFragment,
               bundleOf(MainFragment.MY_INTEREST_LIST to interestList))
         }

         else -> {
            findNavController().navigate(
               R.id.action_extracurricularListFragment_to_contentsFilterFragment,
               bundleOf(MainFragment.MY_INTEREST_LIST to null))
         }
      }
   }

   private fun goToDetail(contentId: Int) {
      findNavController().navigate(
         R.id.action_extracurricularListFragment_to_contestExtracurricularDetailFragment,
         bundleOf("channelType" to ChannelType.Extracurricular.typeEng, "contentId" to contentId))
   }

   private fun getExtracurricularList(includeClosed: Boolean) {
      lifecycleScope.launch {
         val token = UserPreference.accessToken
         var lastRecruitDate: String? = null

         filterViewModel.selectedDate?.let {
            val selectedDateTime = LocalDateTime.of(it.year, it.month, it.dayOfMonth, 23, 59, 59)
            val st = selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            lastRecruitDate = st
         }

         viewModel.getExtraCurricularList(
            token = "Bearer $token",
            type = ChannelType.Extracurricular.typeEng,
            order = orderType,
            interest = interestList ?: null,
            lastRecruitDate = lastRecruitDate ?: null,
            includeClosed = includeClosed).collectLatest { pagingData ->
            adapter.submitData(pagingData)
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
         }
      }
   }
}