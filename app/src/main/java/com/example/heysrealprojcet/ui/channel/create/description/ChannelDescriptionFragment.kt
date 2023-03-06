package com.example.heysrealprojcet.ui.channel.create.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDescriptionFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelDescriptionFragment : Fragment() {
   private lateinit var binding: ChannelDescriptionFragmentBinding
   private val viewModel: ChannelDescriptionViewModel by viewModels()

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
      binding = ChannelDescriptionFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnPreview.setOnClickListener {
         ChannelPreference.channelActivity = viewModel.channelActivity.value.toString()
         ChannelPreference.channelMember = viewModel.channelMember.value.toString()
         ChannelPreference.link1 = viewModel.link1.value.toString()
         viewModel.link2.observe(viewLifecycleOwner) {
            it?.let { ChannelPreference.link2 = it }
         }
         goToChannelPreview()
      }

      binding.addLinkView.setOnClickListener {
         binding.additionalLinkView.visibility = View.VISIBLE
         binding.addButtonContainer.visibility = View.GONE
      }

      binding.removeButton.setOnClickListener {
         binding.addButtonContainer.visibility = View.VISIBLE
         binding.additionalLinkView.visibility = View.GONE
      }
   }

   private fun goToChannelPreview() {
      findNavController().navigate(R.id.action_channelDescriptionFragment_to_channelPreviewFragment)
   }
}