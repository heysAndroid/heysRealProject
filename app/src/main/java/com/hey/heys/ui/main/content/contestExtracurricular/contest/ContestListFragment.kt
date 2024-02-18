package com.hey.heys.ui.main.content.contestExtracurricular.contest

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
import com.hey.heys.databinding.ContestListFragmentBinding
import com.hey.heys.enums.ChannelType
import com.hey.heys.enums.ContentOrder
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.ui.main.MainFragment.Companion.MY_INTEREST_LIST
import com.hey.heys.ui.main.content.contestExtracurricular.filter.ContentsFilterViewModel
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding
   val viewModel by viewModels<ContestListViewModel>()
   private lateinit var adapter: ContestAdapter

   private lateinit var filterViewModel: ContentsFilterViewModel
   private lateinit var interestList: ArrayList<String>
   private var orderType = "Default"
   val args: ContestListFragmentArgs by navArgs()

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
      binding = ContestListFragmentBinding.inflate(inflater, container, false)
      filterViewModel = ViewModelProvider(requireActivity())[ContentsFilterViewModel::class.java]
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      setInterestList()
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.filterButton.setOnClickListener { goToFilter() }

      filterViewModel.selectedInterest.observe(viewLifecycleOwner) {
         binding.filterCount.text =
            if (it.isNullOrEmpty()) {
               "0"
            } else {
               "${it.size}"
            }
      }

      adapter = ContestAdapter()
      binding.contestList.adapter = adapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      adapter.onClickListener = { goToDetail(it) }

      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getContestList(!it)
      }

      lifecycleScope.launch {
         adapter.loadStateFlow.distinctUntilChangedBy {
            it.refresh
         }.collectLatest {
            binding.noListImage.isVisible = adapter.snapshot().items.isNullOrEmpty()
         }
      }
   }

   private fun goToFilter() {
      when (args.type) {
         "interest" -> {
            findNavController().navigate(
               R.id.action_contestListFragment_to_contentsFilterFragment,
               bundleOf(MY_INTEREST_LIST to interestList))
         }

         else -> {
            findNavController().navigate(
               R.id.action_contestListFragment_to_contentsFilterFragment,
               bundleOf(MY_INTEREST_LIST to null))
         }
      }
   }

   private fun goToDetail(contentId: Int) {
      findNavController().navigate(
         R.id.action_contestListFragment_to_contestExtracurricularDetailFragment,
         bundleOf("channelType" to ChannelType.Contest.typeEng, "contentId" to contentId))
   }

   private fun setInterestList() {
      interestList = arrayListOf()
      Log.i("type : ", args.type)
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

   private fun getContestList(includedClosed: Boolean) {
      lifecycleScope.launch {
         val token = UserPreference.accessToken
         var lastRecruitDate: String? = null

         filterViewModel.selectedDate?.let {
            val selectedDateTime = LocalDateTime.of(it.year, it.month, it.dayOfMonth, 23, 59, 59)
            val st = selectedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            lastRecruitDate = st
         }

         viewModel.getContestList(
            token = "Bearer $token",
            type = ChannelType.Contest.typeEng,
            order = orderType,
            interest = interestList ?: null,
            lastRecruitDate = lastRecruitDate ?: null,
            includeClosed = includedClosed).collect { pagingData ->
            adapter.submitData(pagingData)
         }
      }
   }
}