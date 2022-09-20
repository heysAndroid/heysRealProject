package com.example.heysrealprojcet.ui.main.content.study.hey.waiting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ApprovalCancelBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ApprovalCancelBottomSheet(context: Context) : BottomSheetDialogFragment() {
   private lateinit var binding: ApprovalCancelBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ApprovalCancelBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      setupSpinner()
      setupSpinnerHandler()
   }

   private fun setupSpinner() {
      val cancelReasons = resources.getStringArray(R.array.spinner_approval_cancel)

      val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item_view) {
         override fun getCount(): Int {
            return super.getCount() - 1
         }
      }
      adapter.addAll(cancelReasons.toMutableList())
      binding.spinner.adapter = adapter
      binding.spinner.setSelection(adapter.count)
   }

   private fun setupSpinnerHandler() {
      binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
         override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {
         }
      }
   }
}