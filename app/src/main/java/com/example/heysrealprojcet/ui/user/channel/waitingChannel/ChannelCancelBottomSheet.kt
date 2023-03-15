package com.example.heysrealprojcet.ui.user.channel.waitingChannel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.core.view.isVisible
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelCancelBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChannelCancelBottomSheet(context: Context) : BottomSheetDialogFragment() {
   private lateinit var binding: ChannelCancelBottomSheetBinding


   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelCancelBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      binding.spinnerContainer.setOnClickListener {
         // TODO
         binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_close)
      }
      binding.closeButton.setOnClickListener { dismiss() }
      binding.okButton.setOnClickListener {
         // TODO
         // 취소 처리
         dismiss()
      }
      setupSpinner()
      setupSpinnerHandler()
   }

   private fun setupSpinner() {
      val cancelReasons = resources.getStringArray(R.array.spinner_approval_cancel)
      val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item_view, R.id.spinnerText) {
         override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)

            if (position == count) {
               (view.findViewById<View>(R.id.spinnerCheckBox) as CheckBox).isVisible = false
            }

            return view
         }

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

            //Log.e("태그", adapterView?.selectedView.toString())

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