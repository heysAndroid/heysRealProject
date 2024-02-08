package com.hey.heys.ui.channel.create.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hey.heys.R
import com.hey.heys.databinding.ChannelNameFragmentBinding
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.ChannelPreference

class ChannelNameFragment : Fragment() {
   private lateinit var binding: ChannelNameFragmentBinding
   private val viewModel: ChannelNameViewModel by viewModels()
   val args: ChannelNameFragmentArgs by navArgs()

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
      binding = ChannelNameFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      ChannelPreference.reset()

      ChannelPreference.contentId = args.contentId
      ChannelPreference.channelType = args.channelType

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnNext.setOnClickListener {
         ChannelPreference.channelName = viewModel.name.value.toString()
         goToInform()
      }
   }

   private fun goToInform() {
      findNavController().navigate(R.id.action_channelNameFragment_to_channelInformFragment)
   }
}