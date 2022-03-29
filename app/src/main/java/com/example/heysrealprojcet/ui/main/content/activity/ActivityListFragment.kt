package com.example.heysrealprojcet.ui.main.content.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ActivityListFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.contest.ContestItemRecyclerViewAdapter

class ActivityListFragment : Fragment() {
   private lateinit var binding: ActivityListFragmentBinding

   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var contestItemRecyclerViewAdapter: ContestItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>
   private lateinit var hostList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ActivityListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      categoryDetailRecyclerViewAdapter = CategoryDetailRecyclerViewAdapter(type = typeList)
      binding.categoryList.adapter = categoryDetailRecyclerViewAdapter
      binding.categoryList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      contestItemRecyclerViewAdapter = ContestItemRecyclerViewAdapter(host = hostList) { goToDetail() }
      binding.activityList.adapter = contestItemRecyclerViewAdapter
      binding.activityList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun makeList() {
      typeList = mutableListOf("관심 분야", "마감 임박", "많이 찾는", "새로 열린")
      hostList = mutableListOf("주최/주관", "동덕 드림 캠퍼스타운사업단", "과학기술정보통신부")
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_activityListFragment_to_contestFilterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_activityListFragment_to_contestDetailFragment)
   }
}