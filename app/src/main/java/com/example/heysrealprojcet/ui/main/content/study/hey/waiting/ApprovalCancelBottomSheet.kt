package com.example.heysrealprojcet.ui.main.content.study.hey.waiting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ApprovalCancelBottomSheet(context: Context) : BottomSheetDialogFragment() {
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      super.onCreateView(inflater, container, savedInstanceState)
      return inflater.inflate(R.layout.approval_cancel_bottom_sheet, container, false)
   }
}