package com.example.heysrealprojcet.ui.main.content.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.StudyListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelPurpose
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.enums.ChannelRegion
import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.ui.main.MainActivity

class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   private lateinit var studyList: MutableList<Study>

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
      binding = StudyListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      if (studyList.isNotEmpty()) {
         studyItemRecyclerViewAdapter = StudyItemRecyclerViewAdapter(type = studyList) { goToDetail() }
         binding.studyList.adapter = studyItemRecyclerViewAdapter
         binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      } else {
         binding.noListImage.isVisible = true
      }

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun makeList() {
      studyList = mutableListOf(
         Study(
            "스터디 테스트1", arrayListOf(ChannelPurpose.Networking.purpose), ChannelForm.Online.form, ChannelRegion.Gangwon.region, 30, "2023-03-07T14:16:40.573Z",
            ChannelRecruitmentMethod.Approval.method, "소개글", "멤버 모집글", "", arrayListOf("www.kakao.com"), arrayListOf("패션", "IT/SW")),
         Study(
            "스터디 테스트2", arrayListOf(ChannelPurpose.Capability.purpose), ChannelForm.Offline.form, ChannelRegion.Gyeonggi.region, 7, "2023-03-15T11:39:40.573Z",
            ChannelRecruitmentMethod.Decide.method, "소개글", "멤버 모집글", "", arrayListOf("www.github.com"), arrayListOf("패션", "IT/SW")),
         Study(
            "스터디 테스트3", arrayListOf(ChannelPurpose.Experience.purpose), ChannelForm.Both.form, ChannelRegion.Seoul.region, 24, "2023-03-07T14:16:40.573Z",
            ChannelRecruitmentMethod.Approval.method, "소개글", "멤버 모집글", "", arrayListOf("www.kakao.com"), arrayListOf("패션", "IT/SW")),
      )
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_studyFilterFragment)
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_studyFragment_to_heysChannelDetailFragment)
   }
}