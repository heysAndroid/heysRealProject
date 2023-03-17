package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestListFragmentBinding
import com.example.heysrealprojcet.model.Contest
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.MainFragment.Companion.MY_INTEREST_LIST

class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding
   private lateinit var contestItemRecyclerViewAdapter: ContestItemRecyclerViewAdapter
   private lateinit var hostList: MutableList<Contest>
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

      makeList()
      if (hostList.isNotEmpty()) {
         contestItemRecyclerViewAdapter = ContestItemRecyclerViewAdapter(host = hostList) { goToDetail() }
         binding.contestList.adapter = contestItemRecyclerViewAdapter
         binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      } else {
         binding.noListImage.isVisible = true
      }

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun makeList() {
      hostList = mutableListOf(
         Contest(3, R.drawable.bg_study_list, "제 3회 방구석 아이디어톤 모집", "므모 MMO\n", 3),
         Contest(10, R.drawable.bg_study_list, "2022 Q4 MPED 국제 아트앤디자..", "MPED / emdash, MyProject", 500),
         Contest(5, R.drawable.bg_study_list, "아산관광 숏폼 공모전 (찰나의 아산)", "아산시", 1000),
         Contest(2, R.drawable.bg_study_list, "2022년 K-이노스 창업아이디어경진..", "건국대학교 캠퍼스타운 사업단", 2)
      )
   }

   private fun goToFilter() {
      findNavController().navigate(
         R.id.action_contestListFragment_to_contestExtracurricularFilterFragment,
         bundleOf(MY_INTEREST_LIST to myInterestList))
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_contestListFragment_to_contestExtracurricularDetailFragment)
   }
}