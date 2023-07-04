package com.example.heysrealprojcet.ui.user.channel.waitingChannel.cancel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelCancelBottomSheetBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.SimpleResponse
import com.example.heysrealprojcet.util.UserPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelCancelBottomSheet(private val channelId: Int) : BottomSheetDialogFragment() {
   private lateinit var binding: ChannelCancelBottomSheetBinding
   private val viewModel: ChannelCancelViewModel by viewModels()
   private lateinit var listener: ChannelCancelClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelCancelBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.vm = viewModel

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      binding.btnClose.setOnClickListener { dismiss() }
      binding.btnCancel.setOnClickListener {
         viewModel.reason.observe(viewLifecycleOwner) {
            putMemberAbort(it)
         }
      }
      setupSpinner()

      viewModel.reason.observe(viewLifecycleOwner) {
         if (it == "참여 요청을 취소하는 이유를 알려주세요.") {
            setCancelButtonDisabled()
         }
      }
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
      setupSpinnerHandler()

      binding.spinner.viewTreeObserver?.addOnWindowFocusChangeListener { hasFocus ->
         // true -> 닫힘, false -> 열림
         if (hasFocus) {
            binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_expand)
            binding.view.visibility = View.GONE
         } else {
            binding.spinnerArrow.setImageResource(R.drawable.ic_dropdown_close)
            binding.view.visibility = View.VISIBLE
         }
      }
   }

   private fun setupSpinnerHandler() {
      binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
         override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            val checkedTextView = adapterView?.selectedView as CheckedTextView
            checkedTextView.checkMarkDrawable = null

            val selectedView = adapterView?.selectedView as TextView
            if (selectedView.text == "작성하기") {
               binding.flEdittext.visibility = View.VISIBLE
               viewModel.setReason("")
               viewModel.reason.observe(viewLifecycleOwner) {
                  if (!it.isNullOrEmpty()) {
                     setCancelButtonEnabled()
                  } else {
                     setCancelButtonDisabled()
                  }
               }
            } else {
               binding.flEdittext.visibility = View.GONE
               viewModel.setReason(selectedView.text as String)
               setCancelButtonEnabled()
            }
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {

         }
      }
   }

   private fun setCancelButtonEnabled() {
      binding.btnCancel.isEnabled = true
      binding.btnCancel.setBackgroundResource(R.drawable.bg_ok_button_enabled)
   }

   private fun setCancelButtonDisabled() {
      binding.btnCancel.isEnabled = false
      binding.btnCancel.setBackgroundResource(R.drawable.bg_ok_button_disabled)
   }

   private fun putMemberAbort(cancelReason: String) {
      viewModel.putMemberAbort("Bearer ${UserPreference.accessToken}", channelId, SimpleResponse(message = cancelReason)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("putMemberAbort: ", response.data?.message.toString())
               listener.onClick()
               dismiss()
            }

            is NetworkResult.Error -> {
               Log.w("putMemberAbort: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("putMemberAbort: ", "loading")
            }
         }
      }
   }

   fun setOnOKClickListener(listener: () -> Unit) {
      this.listener = object : ChannelCancelClickListener {
         override fun onClick() {
            listener()
         }
      }
   }

   interface ChannelCancelClickListener {
      fun onClick()
   }
}