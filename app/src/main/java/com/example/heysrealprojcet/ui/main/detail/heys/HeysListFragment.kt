package com.example.heysrealprojcet.ui.main.detail.heys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.HeysListFragmentBinding
import com.example.heysrealprojcet.ui.main.content.study.StudyItemRecyclerViewAdapter

class HeysListFragment : Fragment() {
   private lateinit var binding: HeysListFragmentBinding
   private lateinit var heysItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var startDateList: MutableList<String>

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = HeysListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      makeList()
      heysItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(startDate = startDateList)
      binding.heysList.adapter = heysItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      startDateList = mutableListOf("개설한지 6일", "개설한지 90일", "개설한지 7일")
   }
}