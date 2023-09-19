package com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ApprovedUserListFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.channel.profile.UserProfileDialog
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApprovedUserListFragment : Fragment() {
   private lateinit var binding: ApprovedUserListFragmentBinding
   private lateinit var adpater: ApprovedUserListRecyclerViewAdapter
   private val viewModel: ApprovedUserListViewModel by viewModels()
   private val args: ApprovedUserListFragmentArgs by navArgs()

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
      binding = ApprovedUserListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getApprovedUserList(args.channelId)
      adpater = ApprovedUserListRecyclerViewAdapter { getUsers(it) }

      viewModel.approvedUserList.observe(viewLifecycleOwner) {
         binding.noListImage.isVisible = it.isEmpty()
         adpater.submitList(it)
         binding.tvUserNum.text = "${it.size}"
      }

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.rvApprovedUserList.adapter = adpater
      binding.rvApprovedUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun getApprovedUserList(channelId: Int) {
      viewModel.getChannelFollower("Bearer ${UserPreference.accessToken}", channelId, "Approved").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("getChannelFollower: ", response.data?.message.toString())
               response.data?.channelFollower?.let { viewModel.setApprovedUser(it) }
            }

            is NetworkResult.Error -> {
               Log.w("getChannelFollower: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getChannelFollower: ", "loading")
            }
         }
      }
   }

   private fun getUsers(userId: Int) {
      viewModel.getUsers("Bearer ${UserPreference.accessToken}", userId).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("getChannelFollower: ", response.data?.message.toString())
               val userProfileDialog = response.data?.user?.let { UserProfileDialog(requireContext(), it) }
               userProfileDialog?.show(childFragmentManager, null)
            }

            is NetworkResult.Error -> {
               Log.w("getChannelFollower: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getChannelFollower: ", "loading")
            }
         }
      }
   }
}