package com.example.heysrealprojcet.ui.main.content.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.StudyFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter

class StudyFragment : Fragment() {
   private lateinit var binding: StudyFragmentBinding

   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>
   private lateinit var startDateList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = StudyFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      categoryDetailRecyclerViewAdapter = CategoryDetailRecyclerViewAdapter(type = typeList)
      binding.categoryList.adapter = categoryDetailRecyclerViewAdapter
      binding.categoryList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      studyItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(startDate = startDateList)
      binding.studyList.adapter = studyItemRecyclerViewAdapter
      binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun makeList() {
      typeList = mutableListOf("관심 분야", "마감 임박", "많이 찾는", "새로 열린")
      startDateList = mutableListOf("개설한지 6일", "개설한지 90일", "개설한지 7일")
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_filterFragment)
   }
}