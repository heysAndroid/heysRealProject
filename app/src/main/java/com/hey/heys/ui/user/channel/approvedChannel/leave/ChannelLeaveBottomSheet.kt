package com.hey.heys.ui.user.channel.approvedChannel.leave

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
import com.hey.heys.R
import com.hey.heys.databinding.ChannelLeaveBottomSheetBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.SimpleResponse
import com.hey.heys.util.UserPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelLeaveBottomSheet(private val channelId: Int) : BottomSheetDialogFragment() {
   private lateinit var binding: ChannelLeaveBottomSheetBinding
   private val viewModel: ChannelLeaveViewModel by viewModels()
   private lateinit var listener: ChannelLeaveClickListener
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelLeaveBottomSheetBinding.inflate(inflater, container, false)
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
      binding.btnLeave.setOnClickListener {
         viewModel.reason.observe(viewLifecycleOwner) {
            putExitChannel(it)
         }
      }
      setupSpinner()

      viewModel.reason.observe(viewLifecycleOwner) {
         if (it == "채널을 나가는 이유를 알려주세요.") {
            setLeaveButtonDisabled()
         }
      }
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
                     setLeaveButtonEnabled()
                  } else {
                     setLeaveButtonDisabled()
                  }
               }
            } else {
               binding.flEdittext.visibility = View.GONE
               viewModel.setReason(selectedView.text as String)
               setLeaveButtonEnabled()
            }
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {

         }
      }
   }

   private fun setLeaveButtonEnabled() {
      binding.btnLeave.isEnabled = true
      binding.btnLeave.setBackgroundResource(R.drawable.bg_ok_button_enabled)
   }

   private fun setLeaveButtonDisabled() {
      binding.btnLeave.isEnabled = false
      binding.btnLeave.setBackgroundResource(R.drawable.bg_ok_button_disabled)
   }

   private fun putExitChannel(exitReason: String) {
      viewModel.putExitChannel("Bearer ${UserPreference.accessToken}", channelId, SimpleResponse(message = exitReason)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("putExitChannel: ", response.data?.message.toString())
               listener.onClick()
               dismiss()
            }

            is NetworkResult.Error -> {
               Log.w("putExitChannel: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("putExitChannel: ", "loading")
            }
         }
      }
   }

   fun setOnOKClickListener(listener: () -> Unit) {
      this.listener = object : ChannelLeaveClickListener {
         override fun onClick() {
            listener()
         }
      }
   }

   interface ChannelLeaveClickListener {
      fun onClick()
   }
}