package com.example.heysrealprojcet.ui.channel

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
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.main.content.study.StudyItemRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.study.StudyViewData

class HeysChannelListFragment : Fragment() {
   private lateinit var binding: HeysChannelListFragmentBinding
   private lateinit var heysItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var studyList: MutableList<StudyViewData>

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
      heysItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(type = studyList) { goToChannelDetail() }
      binding.heysList.adapter = heysItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.makeChannel.setOnClickListener {
         goToChannelList()
      }
   }

   private fun makeList() {
      studyList = mutableListOf(
         StudyViewData(3, R.drawable.sample_image, "수도권 팀원 \n모집해요!", "개설한지 7일", 3),
         StudyViewData(10, R.drawable.sample_image, "칠팔구십일이삼사오...", "개설한지 7일", 500),
         StudyViewData(5, R.drawable.sample_image, "2022 이랜드재단 ESG 서포터즈", "개설한지 7일", 1000),
         StudyViewData(2, R.drawable.sample_image, "제3회 \n연구개발 특구", "개설한지 7일", 2)
      )
   }

   private fun goToChannelList() {
      findNavController().navigate(R.id.action_heysChannelListFragment_to_heysChannelNameFragment)
   }

   private fun goToChannelDetail() {
      findNavController().navigate(R.id.action_heysChannelListFragment_to_heysChannelDetailFragment)
   }
}