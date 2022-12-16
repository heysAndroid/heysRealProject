package com.example.heysrealprojcet.ui.sign_up.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ExistingUserBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExistingUserBottomSheet : BottomSheetDialogFragment() {
   private lateinit var binding: ExistingUserBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ExistingUserBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)

      binding.goToLogin.setOnClickListener { goToLogin() }
   }

   private fun goToLogin() {
      findNavController().navigate(R.id.action_signUpPhoneFragment_to_signInPhoneFragment)
   }
}