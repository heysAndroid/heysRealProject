package com.example.heysrealprojcet.ui.main.content.contestActivity.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ActivityListFragmentBinding
import com.example.heysrealprojcet.model.Activities
import com.example.heysrealprojcet.ui.main.category.Category
import com.example.heysrealprojcet.ui.main.category.CategoryDetailRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityListFragment : Fragment() {
   private lateinit var binding: ActivityListFragmentBinding
   private val viewModel: ActivityListViewModel by viewModels()

   private lateinit var categoryDetailRecyclerViewAdapter: CategoryDetailRecyclerViewAdapter
   private lateinit var activityItemRecyclerViewAdapter: ActivityItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<Category>
   private lateinit var hostList: MutableList<Activities>

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

      activityItemRecyclerViewAdapter = ActivityItemRecyclerViewAdapter(host = hostList) { goToDetail() }
      binding.activityList.adapter = activityItemRecyclerViewAdapter
      binding.activityList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      binding.filterButton.setOnClickListener { goToFilter() }
      binding.pingTest.setOnClickListener { viewModel.ping() }
   }

   private fun makeList() {
      typeList = mutableListOf(
         Category("관심 분야", R.drawable.character_creative_interest, true),
         Category("마감 임박", R.drawable.character_creative_finish, false),
         Category("많이 찾는", R.drawable.character_creative_many, false),
         Category("새로 열린", R.drawable.character_creative_new, false)
      )

      hostList = mutableListOf(
         Activities(3, R.drawable.bg_study_list, "수도권 팀원 \n모집해요!", "개설한지 7일", 3),
         Activities(10, R.drawable.bg_study_list, "칠팔구십일이삼사오...", "일이삼사오육칠팔구이...", 500),
         Activities(5, R.drawable.bg_study_list, "2022 이랜드재단 ESG 서포터즈", "이랜드재단", 1000),
         Activities(2, R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2)
      )
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_activityListFragment_to_contestActivityFilterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_activityListFragment_to_contestActivityDetailFragment)
   }
}