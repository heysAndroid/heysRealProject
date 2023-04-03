package com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.WaitingUserListFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingUserListFragment : Fragment() {
   private lateinit var binding: WaitingUserListFragmentBinding
   private lateinit var adpater: WaitingUserListRecyclerViewAdapter
   private val args: WaitingUserListFragmentArgs by navArgs()

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
      binding = WaitingUserListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      adpater = WaitingUserListRecyclerViewAdapter(args.waitingUser.toMutableList())
      binding.waitingUserList.adapter = adpater
      binding.waitingUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }
}