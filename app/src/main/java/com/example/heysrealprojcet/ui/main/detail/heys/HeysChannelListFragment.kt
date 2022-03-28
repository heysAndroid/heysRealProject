package com.example.heysrealprojcet.ui.main.detail.heys

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
import com.example.heysrealprojcet.ui.main.content.study.StudyItemRecyclerViewAdapter

class HeysChannelListFragment : Fragment() {
   private lateinit var binding: HeysChannelListFragmentBinding
   private lateinit var heysItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var startDateList: MutableList<String>

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
      heysItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(startDate = startDateList) { goToChannelDetail() }
      binding.heysList.adapter = heysItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      startDateList = mutableListOf("개설한지 6일", "개설한지 90일", "개설한지 7일")
   }

   private fun goToChannelDetail() {
      findNavController().navigate(R.id.action_heysChannelListFragment_to_heysChannelDetailFragment)
   }
}