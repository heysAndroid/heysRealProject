package com.example.heysrealprojcet.ui.main.content.study.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.StudyListFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyListFragment : Fragment() {
   private lateinit var binding: StudyListFragmentBinding
   private lateinit var studyItemRecyclerViewAdapter: StudyItemRecyclerViewAdapter
   val viewModel by viewModels<StudyListViewModel>()

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

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = StudyListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      viewModel.channelList.observe(viewLifecycleOwner) {
         binding.noListImage.isVisible = it.isEmpty()
         binding.flCreateStudy.isVisible = it.isNotEmpty()
      }
      viewModel.isChecked.asLiveData().observe(viewLifecycleOwner) {
         getStudyList(!it)
      }

      binding.filterButton.setOnClickListener { goToFilter() }
   }

   private fun getStudyList(includeClosed: Boolean) {
      val token = UserPreference.accessToken
      viewModel.getStudyList("Bearer $token", null, null, null, null, null, includeClosed = includeClosed).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.setStudyList(response.data?.data)
               studyItemRecyclerViewAdapter = viewModel.channelList.value?.toMutableList()?.let {
                  StudyItemRecyclerViewAdapter(it) { id ->
                     goToDetail(id)
                  }
               }!!
               binding.studyList.adapter = studyItemRecyclerViewAdapter
               binding.studyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }

            is NetworkResult.Loading -> {
               Log.w("getStudyList: ", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getStudyList: ", response.message.toString())
            }
         }
      }
   }

   private fun goToFilter() {
      findNavController().navigate(R.id.action_studyFragment_to_studyFilterFragment)
   }

   private fun goToDetail(channelId: Int) {
      findNavController().navigate(
         R.id.action_studyFragment_to_heysChannelDetailFragment,
         bundleOf("channelId" to channelId))
   }
}