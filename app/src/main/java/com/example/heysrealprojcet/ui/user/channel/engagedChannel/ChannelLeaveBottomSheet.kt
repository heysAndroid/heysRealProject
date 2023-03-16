package com.example.heysrealprojcet.ui.user.channel.engagedChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelLeaveBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChannelLeaveBottomSheet : BottomSheetDialogFragment() {
   private lateinit var binding: ChannelLeaveBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelLeaveBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED
      binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_expand)
      binding.closeButton.setOnClickListener { dismiss() }
      binding.okButton.setOnClickListener {
         // TODO
         // 나가기 처리
         dismiss()
      }
      setupSpinner()
   }

   private fun setupSpinner() {
      val cancelReasons = resources.getStringArray(R.array.spinner_channel_leave)
      val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item_view) {
         override fun getCount(): Int {
            return super.getCount() - 1
         }
      }

      adapter.addAll(cancelReasons.toMutableList())
      binding.spinner.adapter = adapter
      binding.spinner.setSelection(adapter.count)
      setupSpinnerHandler()

      binding.spinner.viewTreeObserver?.addOnWindowFocusChangeListener { hasFocus ->
         // true -> 닫힘, false -> 열림
         if (hasFocus) {
            binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_expand)
         } else {
            binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_close)
         }
      }
   }

   private fun setupSpinnerHandler() {
      binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
         override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            val checkedTextView = adapterView?.selectedView as CheckedTextView
            checkedTextView.checkMarkDrawable = null

//            val selectedView = adapterView?.selectedView as TextView
//            if (selectedView.text == "작성하기") {
//               binding.editText.visibility = View.VISIBLE
//               setOkButtonEnabled()
//            } else {
//               binding.editText.visibility = View.INVISIBLE
//               if (selectedView.text.contains("이유")) {
//                  setOkButtonDisabled()
//               } else {
//                  setOkButtonEnabled()
//               }
//            }
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {

         }
      }
   }

   private fun setOkButtonEnabled() {
      binding.okButton.isEnabled = true
      binding.okButton.setBackgroundResource(R.drawable.bg_ok_button_enabled)
   }

   private fun setOkButtonDisabled() {
      binding.okButton.isEnabled = false
      binding.okButton.setBackgroundResource(R.drawable.bg_ok_button_disabled)
   }
}