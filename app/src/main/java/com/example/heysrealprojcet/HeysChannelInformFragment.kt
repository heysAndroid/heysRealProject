package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.databinding.HeysChannelInformFragmentBinding

class HeysChannelInformFragment : Fragment() {
   private lateinit var binding: HeysChannelInformFragmentBinding
   private val viewModel: HeysChannelInformViewModel by viewModels()
   private val purposeDialogViewModel: ChannelPurposeDialogViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelInformFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.purposeContainer.setOnClickListener {
         val purposeDialog = ChannelPurposeDialog(requireContext(), purposeDialogViewModel)
         purposeDialog.setOnOKClickListener { content ->
            binding.purpose.text = content
         }
         purposeDialog.show()
      }

      binding.btnForm.setOnClickListener {
         val customDialog = ChannelFormDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnJoin.setOnClickListener {
         val customDialog = ChannelJoinDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnWay.setOnClickListener {
         val customDialog = ChannelWayDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnNext.setOnClickListener {
         goToName()
      }
   }

   private fun goToName() {
      findNavController().navigate(R.id.action_heysChannelInformFragment_to_heysChannelFreeFragment)
   }
}

