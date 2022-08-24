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
import com.example.heysrealprojcet.databinding.StudyListFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.category.CategoryViewData

class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<CategoryViewData>
   private lateinit var startDateList: MutableList<Int>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = StudyListFragmentBinding.inflate(inflater, container, false)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      categoryDetailRecyclerViewAdapter = CategoryDetailRecyclerViewAdapter(type = typeList)
      binding.categoryList.adapter = categoryDetailRecyclerViewAdapter
      binding.categoryList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      studyItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(startDate = startDateList, type = typeList) {}
      binding.studyList.adapter = studyItemRecyclerViewAdapter
      binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.filterButton.setOnClickListener { goToFilter() }

   }

   private fun makeList() {
      typeList = mutableListOf(
         CategoryViewData("관심 분야", R.drawable.character_passion_interest, true),
         CategoryViewData("마감 임박", R.drawable.character_passion_finish, false),
         CategoryViewData("많이 찾는", R.drawable.character_passion_many, false),
         CategoryViewData("새로 열린", R.drawable.character_passion_new, false)
      )
      startDateList = mutableListOf(3, 10, 5, 2, 0)
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_studyFilterFragment)
   }


}