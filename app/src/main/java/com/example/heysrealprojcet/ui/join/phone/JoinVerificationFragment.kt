package com.example.heysrealprojcet.ui.join.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinPhoneVerificationFragmentBinding

class JoinVerificationFragment : Fragment() {
   private lateinit var binding: JoinPhoneVerificationFragmentBinding
   private val viewModel: JoinVerificationViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      viewModel.phoneNumber.value = arguments?.getString("phoneNumber")

      if(viewModel.phoneNumber.value!!.isNotEmpty()) {
         viewModel.timerStart()
      }

      binding.okButton.setOnClickListener {
         findNavController().navigate(R.id.action_phoneVerificationFragment_to_joinPasswordFragment)
      }
   }
}