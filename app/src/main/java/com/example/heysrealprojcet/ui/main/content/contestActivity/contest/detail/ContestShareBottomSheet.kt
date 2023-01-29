package com.example.heysrealprojcet.ui.main.content.contestActivity.contest.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.CustomSnackBar
import com.example.heysrealprojcet.databinding.ContestShareBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContestShareBottomSheet : BottomSheetDialogFragment() {
   private lateinit var binding: ContestShareBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ContestShareBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      binding.btnClose.setOnClickListener { dismiss() }

      binding.btnKakao.setOnClickListener {
         // TODO
         dismiss()
      }

      binding.btnInstagram.setOnClickListener {
         // TODO
         dismiss()
      }

      binding.btnClip.setOnClickListener {
         // TODO 링크 복사
         CustomSnackBar(requireParentFragment().requireView(), "공유 링크를 복사했어요!", null).show()
         dismiss()
      }
   }

   // 링크 복사
   private fun createClipData(clipData: String) {
      val clipboard  = requireContext().applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText(clipData, clipData)
      clipboard.setPrimaryClip(clip)
   }
}