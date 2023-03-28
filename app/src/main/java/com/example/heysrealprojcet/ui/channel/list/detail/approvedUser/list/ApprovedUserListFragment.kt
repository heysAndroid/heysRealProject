package com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ApprovedUserListFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApprovedUserListFragment() : Fragment() {
   private lateinit var binding: ApprovedUserListFragmentBinding
   private lateinit var adpater: ApprovedUserListRecyclerViewAdapter
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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ApprovedUserListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      adpater = ApprovedUserListRecyclerViewAdapter(args.approvedUser.toMutableList())
      binding.approvedUserList.adapter = adpater
      binding.approvedUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }
}