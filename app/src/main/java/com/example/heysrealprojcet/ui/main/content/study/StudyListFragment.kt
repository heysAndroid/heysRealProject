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
import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.category.Category
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter

class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<Category>
   private lateinit var studyList: MutableList<Study>

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

      studyItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(type = studyList) { goToDetail() }
      binding.studyList.adapter = studyItemRecyclerViewAdapter
      binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.filterButton.setOnClickListener { goToFilter() }

   }

   private fun makeList() {
      typeList = mutableListOf(
         Category("관심 분야", R.drawable.character_personal_interest, true),
         Category("마감 임박", R.drawable.character_personal_finish, false),
         Category("많이 찾는", R.drawable.character_personal_many, false),
         Category("새로 열린", R.drawable.character_personal_new, false)
      )

      studyList = mutableListOf(
         Study(3, R.drawable.bg_study_list, "수도권 팀원 \n모집해요!", "개설한지 7일", 3),
         Study(10, R.drawable.bg_study_list, "칠팔구십일이삼사오...", "일이삼사오육칠팔구이...", 500),
         Study(5, R.drawable.bg_study_list, "2022 이랜드재단 ESG 서포터즈", "이랜드재단", 1000),
         Study(2, R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2)
      )
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_studyFilterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_studyFragment_to_heysChannelDetailFragment)
   }
}