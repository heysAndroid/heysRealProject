package com.example.heysrealprojcet.ui.join.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinPhoneFragmentBinding

class JoinPhoneFragment : Fragment() {
   private lateinit var binding: JoinPhoneFragmentBinding
   private val viewModel: JoinPhoneViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinPhoneFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.okButton.setOnClickListener {
         findNavController().navigate(R.id.action_joinPhoneFragment_to_phoneVerificationFragment)
      }
   }
}