package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ExtracurricularListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.enums.ContentOrder
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.MainFragment
import com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter.ContestExtracurricularFilterViewModel
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtracurricularListFragment : Fragment() {
   private lateinit var binding: ExtracurricularListFragmentBinding
   private val viewModel: ExtracurricularListViewModel by viewModels()
   private lateinit var extraCurricularItemRecyclerViewAdapter: ExtracurricularItemRecyclerViewAdapter

   lateinit var filterViewModel: ContestExtracurricularFilterViewModel
   private lateinit var myInterestList: ArrayList<String>
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
      filterViewModel = ViewModelProvider(requireActivity())[ContestExtracurricularFilterViewModel::class.java]
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel

      filterViewModel.interestArray.forEach {
         Log.w("filters: ", it)
      }
      setInterestList()

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.filterCount.text = "${myInterestList.size}"
      binding.filterButton.setOnClickListener { goToFilter() }

      viewModel.contestList.observe(viewLifecycleOwner) { binding.noListImage.isVisible = it.isEmpty() }
      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getExtraCurricularList(orderType, myInterestList, !it)
      }
   }

   private fun setInterestAndType() {
      myInterestList = arrayListOf()
      when (args.type) {
         "interest" -> {
            UserPreference.interests.split(",").forEach {
               myInterestList.add(it)
            }
            orderType = ContentOrder.Default.order
         }

         else -> {
            filterViewModel.interestArray.forEach {
               myInterestList.add(it)
            }
            orderType = args.type
         }
      }
      getExtraCurricularList(orderType, interests = myInterestList, includeClosed = !viewModel.isChecked.value)
   }

   private fun goToFilter() {
      when (args.type) {
         "interest" -> {
            findNavController().navigate(
               R.id.action_extracurricularListFragment_to_contentsFilterFragment,
               bundleOf(MainFragment.MY_INTEREST_LIST to myInterestList))
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

   private fun getExtraCurricularList(order: String? = ContentOrder.Default.order, interests: ArrayList<String>, includeClosed: Boolean) {
      val token = UserPreference.accessToken
      var lastRecruitDate: String? = null
      if (filterViewModel.selectedDate != null) {
         lastRecruitDate = filterViewModel.calendarDate.value.toString() + "T23:59:59"
      }
      interests.forEach { Log.i("interests: ", it) }

      viewModel.getExtraCurricularList("Bearer $token", ChannelType.Extracurricular.typeEng, order, interests, lastRecruitDate, includeClosed).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setContestList(response.data?.data)
               extraCurricularItemRecyclerViewAdapter = viewModel.contestList.value?.toMutableList()?.let {
                  ExtracurricularItemRecyclerViewAdapter(it) { contentID ->
                     goToDetail(contentID)
                  }
               }!!
               binding.extracurricularList.adapter = extraCurricularItemRecyclerViewAdapter
               binding.extracurricularList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }

            is NetworkResult.Loading -> {
               Log.w("getExtraList: ", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getExtraList: ", response.message.toString())
            }
         }
      }
   }
}