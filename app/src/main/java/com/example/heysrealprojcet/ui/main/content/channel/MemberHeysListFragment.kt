package com.example.heysrealprojcet.ui.main.content.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.MemberHeysListFragmentBinding

class MemberHeysListFragment : Fragment() {
   private lateinit var binding: MemberHeysListFragmentBinding
   private lateinit var memberHeysItemRecyclerViewAdapter: MemberHeysItemRecyclerViewAdapter
   private lateinit var userNameList: MutableList<String>

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = MemberHeysListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      makeList()
      memberHeysItemRecyclerViewAdapter = MemberHeysItemRecyclerViewAdapter(name = userNameList) { }
      binding.heysList.adapter = memberHeysItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      userNameList = mutableListOf("이름1", "이름2", "이름3", "이름4", "이름5")
   }
}