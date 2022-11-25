package com.example.heysrealprojcet.ui.channel.create.description

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDescriptionFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

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

      //EditText 글자 제한
      binding.edtIntroduce.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
      binding.btnPreview.setOnClickListener { goToChannelInterest() }
   }

   private fun goToChannelInterest() {
      findNavController().navigate(R.id.action_channelDescriptionFragment_to_channelInterestFragment)
   }
}