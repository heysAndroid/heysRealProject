package com.example.heysrealprojcet.ui.main.content.contestActivity.contest

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
import com.example.heysrealprojcet.model.Contest
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.category.Category
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter

class ContestListFragment : Fragment() {
   private lateinit var binding: ContestListFragmentBinding
   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var contestItemRecyclerViewAdapter: ContestItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<Category>
   private lateinit var hostList: MutableList<Contest>

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
      typeList = mutableListOf(
         Category("관심 분야", R.drawable.character_passion_interest, true),
         Category("마감 임박", R.drawable.character_passion_finish, false),
         Category("많이 찾는", R.drawable.character_passion_many, false),
         Category("새로 열린", R.drawable.character_passion_new, false)
      )

      hostList = mutableListOf(
         Contest(3, R.drawable.bg_study_list, "수도권 팀원 \n모집해요!", "개설한지 7일", 3),
         Contest(10, R.drawable.bg_study_list, "칠팔구십일이삼사오...", "일이삼사오육칠팔구이...", 500),
         Contest(5, R.drawable.bg_study_list, "2022 이랜드재단 ESG 서포터즈", "이랜드재단", 1000),
         Contest(2, R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2)
      )
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_contestListFragment_to_contestActivityFilterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_contestListFragment_to_contestActivityDetailFragment)
   }
}