package com.example.heysrealprojcet.ui.main.content.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestListFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter

class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding

   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var contestItemRecyclerViewAdapter: ContestItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>
   private lateinit var hostList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ContestListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      categoryDetailRecyclerViewAdapter = CategoryDetailRecyclerViewAdapter(type = typeList)
      binding.categoryList.adapter = categoryDetailRecyclerViewAdapter
      binding.categoryList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      contestItemRecyclerViewAdapter = ContestItemRecyclerViewAdapter(host = hostList) { goToDetail() }
      binding.contestList.adapter = contestItemRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun makeList() {
      typeList = mutableListOf("관심 분야", "마감 임박", "많이 찾는", "새로 열린")
      hostList = mutableListOf("주최/주관", "동덕 드림 캠퍼스타운사업단", "과학기술정보통신부")
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_contestListFragment_to_filterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_contestListFragment_to_contestDetailFragment)
   }
}