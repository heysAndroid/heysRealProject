package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.MainFragmentBinding

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding

   private lateinit var contestRecyclerViewAdapter: ContestRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      makeList()
      contestRecyclerViewAdapter = ContestRecyclerViewAdapter(type = typeList)
      binding.contestList.adapter = contestRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

   }

   private fun makeList() {
      typeList = mutableListOf("관심분야별", "마감 임박", "많이 찾는", "새로 열린")
   }
}