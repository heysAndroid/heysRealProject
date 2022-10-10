package com.example.heysrealprojcet.ui.channel.create.description

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

      viewModel.isEnabled.observe(viewLifecycleOwner) {
         if (it) {
            binding.letterCount.text = "${binding.edtIntroduce.length()}"
            binding.edtIntroduce.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_channel_edittext_focused)
            binding.btnPreview.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_f7bc26_radius_8)
         } else {
            binding.btnPreview.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_e1_radius_8)
         }
      }

      binding.btnPreview.setOnClickListener {
         goToFreePreview()
      }
   }

   private fun goToFreePreview() {
      findNavController().navigate(R.id.action_heysChannelFreeFragment_to_heysChannelFreePreviewFragment)
   }
}