package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest

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
import com.example.heysrealprojcet.databinding.ContestListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.MainFragment.Companion.MY_INTEREST_LIST
import com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter.ContestExtracurricularFilterViewModel
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding

   private lateinit var contentItemRecyclerViewAdapter: ContentItemRecyclerViewAdapter
   val viewModel by viewModels<ContestListViewModel>()
   lateinit var filterViewModel: ContestExtracurricularFilterViewModel

   private lateinit var myInterestList: ArrayList<String>
   val args: ContestListFragmentArgs by navArgs()
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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ContestListFragmentBinding.inflate(inflater, container, false)
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
      binding.filterCount.text = "${myInterestList.size}"
      viewModel.contestList.observe(viewLifecycleOwner) { binding.noListImage.isVisible = it.isEmpty() }
      binding.filterButton.setOnClickListener { goToFilter() }

      viewModel.isCheked.asLiveData().observe(viewLifecycleOwner) {
         getContestList(myInterestList, !it)
      }
   }

   private fun goToFilter() {
      when (args.type) {
         "interest" -> {
            findNavController().navigate(
               R.id.action_contestListFragment_to_contestExtracurricularFilterFragment,
               bundleOf(MY_INTEREST_LIST to myInterestList))
         }

         else -> {
            findNavController().navigate(
               R.id.action_contestListFragment_to_contestExtracurricularFilterFragment,
               bundleOf(MY_INTEREST_LIST to null))
         }
      }

   }

   private fun goToDetail(contentId: Int) {
      findNavController().navigate(R.id.action_contestListFragment_to_contestExtracurricularDetailFragment, bundleOf("contentID" to contentId))
   }

   private fun setInterestList() {
      myInterestList = arrayListOf()
      Log.i("type: ", args.type)
      when (args.type) {
         "all" -> {
            // do nothing
         }

         "default" -> {
            filterViewModel.interestArray.forEach {
               myInterestList.add(it)
            }
         }

         "interest" -> {
            UserPreference.interests.split(",").forEach {
               myInterestList.add(it)
            }
         }
      }
      getContestList(myInterestList, !viewModel.isCheked.value)
   }

   private fun getContestList(interests: ArrayList<String>, includeClosed: Boolean) {
      val token = UserPreference.accessToken
      interests.forEach {
         Log.i("interests: ", it)
      }
      viewModel.getContestList("Bearer $token", ChannelType.Contest.typeEng, interests, null, includeClosed).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setContestList(response.data?.data)
               contentItemRecyclerViewAdapter = viewModel.contestList.value?.toMutableList()?.let {
                  ContentItemRecyclerViewAdapter(it) { contentID ->
                     goToDetail(contentID)
                  }
               }!!
               binding.contestList.adapter = contentItemRecyclerViewAdapter
               binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }

            is NetworkResult.Loading -> {
               Log.w("getContestList: ", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getContestList: ", response.message.toString())
            }
         }
      }

   }
}