package com.hey.heys.ui.channel.list.detail.waitingUser.reject

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
import com.hey.heys.databinding.ChannelRejectBottomSheetBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.SimpleResponse
import com.hey.heys.util.UserPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelRejectBottomSheet(private val channelId: Int, private val followerId: Int) : BottomSheetDialogFragment() {
   private lateinit var binding: ChannelRejectBottomSheetBinding
   private val viewModel: ChannelRejectViewModel by viewModels()
   private lateinit var listener: ChannelRejectClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelRejectBottomSheetBinding.inflate(inflater, container, false)
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
      binding.btnReject.setOnClickListener {
         viewModel.reason.observe(viewLifecycleOwner) {
            putRequestReject(it)
         }
      }
      setupSpinner()

      viewModel.reason.observe(viewLifecycleOwner) {
         if (it == "승인 요청을 취소하는 이유를 알려주세요.") {
            setRejectButtonDisabled()
         }
      }
   }

   private fun setupSpinner() {
      val rejectReasons = resources.getStringArray(R.array.spinner_channel_reject)
      val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item_view) {
         override fun getCount(): Int {
            return super.getCount() - 1
         }
      }

      adapter.addAll(rejectReasons.toMutableList())
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
                     setRejectButtonEnabled()
                  } else {
                     setRejectButtonDisabled()
                  }
               }
            } else {
               binding.flEdittext.visibility = View.GONE
               viewModel.setReason(selectedView.text as String)
               setRejectButtonEnabled()
            }
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {

         }
      }
   }

   private fun setRejectButtonEnabled() {
      binding.btnReject.isEnabled = true
      binding.btnReject.setBackgroundResource(R.drawable.bg_ok_button_enabled)
   }

   private fun setRejectButtonDisabled() {
      binding.btnReject.isEnabled = false
      binding.btnReject.setBackgroundResource(R.drawable.bg_ok_button_disabled)
   }

   private fun putRequestReject(rejectReason: String) {
      viewModel.putRequestReject("Bearer ${UserPreference.accessToken}", channelId, followerId, SimpleResponse(message = rejectReason)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("putRequestReject: ", response.data?.message.toString())
               listener.onClick()
               dismiss()
            }

            is NetworkResult.Error -> {
               Log.w("putRequestReject: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("putRequestReject: ", "loading")
            }
         }
      }
   }

   fun setOnOKClickListener(listener: () -> Unit) {
      this.listener = object : ChannelRejectClickListener {
         override fun onClick() {
            listener()
         }
      }
   }

   interface ChannelRejectClickListener {
      fun onClick()
   }
}