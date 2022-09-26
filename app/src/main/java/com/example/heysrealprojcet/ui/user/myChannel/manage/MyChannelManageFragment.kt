package com.example.heysrealprojcet.ui.user.myChannel.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelManageBinding

class MyChannelManageFragment : Fragment() {
   private lateinit var binding: MyChannelManageBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelManageBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.btnApproval.setOnClickListener { gotoApproval() }
   }

   private fun gotoApproval() {
      findNavController().navigate(R.id.action_myChannelManageFragment_to_manageApprovalFragment)
   }
}