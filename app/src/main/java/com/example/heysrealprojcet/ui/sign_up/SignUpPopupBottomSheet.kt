package com.example.heysrealprojcet.ui.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.databinding.SignUpPopupBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpPopupBottomSheet : BottomSheetDialogFragment() {
   private lateinit var binding: SignUpPopupBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SignUpPopupBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      binding.nextButton.setOnClickListener {
         val intent = Intent(requireContext(), SignUpActivity::class.java)
         startActivity(intent)
         requireActivity().finish()
      }
   }
}