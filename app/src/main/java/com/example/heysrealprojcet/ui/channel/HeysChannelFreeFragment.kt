package com.example.heysrealprojcet.ui.channel

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
import com.example.heysrealprojcet.databinding.HeysChannelFreeFragmentBinding
import com.example.heysrealprojcet.ui.channel.viewModel.HeysChannelFreeViewModel

class HeysChannelFreeFragment : Fragment() {
   private lateinit var binding: HeysChannelFreeFragmentBinding
   private val viewModel: HeysChannelFreeViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelFreeFragmentBinding.inflate(inflater, container, false)
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