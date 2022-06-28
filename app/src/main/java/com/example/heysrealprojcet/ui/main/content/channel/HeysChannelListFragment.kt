package com.example.heysrealprojcet.ui.main.content.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.HeysChannelListFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryViewData
import com.example.heysrealprojcet.ui.main.content.study.StudyItemRecyclerViewAdapter

class HeysChannelListFragment : Fragment() {
   private lateinit var binding: HeysChannelListFragmentBinding
   private lateinit var heysItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var typeList: MutableList<CategoryViewData>
   private lateinit var startDateList: MutableList<Int>

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = HeysChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      makeList()
      heysItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(startDate = startDateList, type = typeList) { goToChannelDetail() }
      binding.heysList.adapter = heysItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
//      typeList = mutableListOf(
//         CategoryViewData("관심 분야", R.drawable.character_passion_interest, true),
//         CategoryViewData("마감 임박", R.drawable.character_passion_finish, false),
//         CategoryViewData("많이 찾는", R.drawable.character_passion_many, false),
//         CategoryViewData("새로 열린", R.drawable.character_passion_new, false)
//      )

//      startDateList = mutableListOf("개설한지 6일", "개설한지 90일", "개설한지 7일")
   }

   private fun goToChannelDetail() {
      findNavController().navigate(R.id.action_heysChannelListFragment_to_heysChannelDetailFragment)
   }
}