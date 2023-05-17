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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.MainFragment.Companion.MY_INTEREST_LIST
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding
   private lateinit var contestItemRecyclerViewAdapter: ContestItemRecyclerViewAdapter
   val viewModel by viewModels<ContestListViewModel>()

   private lateinit var myInterestList: ArrayList<String>
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
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      myInterestList = arguments?.getStringArrayList(MY_INTEREST_LIST) as ArrayList<String>
      binding.filterCount.text = "${myInterestList.size}"

      getContestList()
      viewModel.contestList.observe(viewLifecycleOwner) { binding.noListImage.isVisible = it.isEmpty() }
      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun goToFilter() {
      findNavController().navigate(
         R.id.action_contestListFragment_to_contestExtracurricularFilterFragment,
         bundleOf(MY_INTEREST_LIST to myInterestList))
   }

   private fun goToDetail(contentId: Int) {
      findNavController().navigate(R.id.action_contestListFragment_to_contestExtracurricularDetailFragment, bundleOf("contentID" to contentId))
   }

   private fun getContestList() {
      val token = UserPreference.accessToken
      viewModel.getContestList("Bearer $token", ChannelType.Contest.typeEng, myInterestList, null, true).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setContestList(response.data?.data)
               contestItemRecyclerViewAdapter = viewModel.contestList.value?.toMutableList()?.let {
                  ContestItemRecyclerViewAdapter(it) { contentID ->
                     goToDetail(contentID)
                  }
               }!!
               binding.contestList.adapter = contestItemRecyclerViewAdapter
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