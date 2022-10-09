package com.example.heysrealprojcet.ui.user.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyPageFragmentBinding

class MyPageFragment : Fragment() {
   private lateinit var binding: MyPageFragmentBinding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = MyPageFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.goToSetting.setOnClickListener { goToSetting() }
      binding.channelContainer.setOnClickListener { goToEngagedChannel() }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_myPageFragment_to_settingFragment)
   }

   private fun goToEngagedChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_engagedChannelListFragment)
   }
}