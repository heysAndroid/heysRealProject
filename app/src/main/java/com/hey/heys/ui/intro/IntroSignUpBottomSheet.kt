package com.hey.heys.ui.intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hey.heys.databinding.SignUpPopupBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IntroSignUpBottomSheet(
   private val doAfterSelectNext: () -> Unit
) : BottomSheetDialogFragment() {
   private lateinit var binding: SignUpPopupBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SignUpPopupBottomSheetBinding.inflate(layoutInflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.tvIndividualPolicy.setOnClickListener {
         startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://caring-fig-dc9.notion.site/715dc699691e4a80994c32e04261e38b")))
      }

      binding.tvServiceTerm.setOnClickListener {
         startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://caring-fig-dc9.notion.site/e92a574c9a0345589f3d652c55c84349?pvs=4")))
      }

      binding.nextButton.setOnClickListener {
         doAfterSelectNext()
         dismiss()
      }
   }
}