package com.example.heysrealprojcet

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.heysrealprojcet.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val message: String, private val anchorView: View) {
   companion object {
      fun make(view: View, message: String, anchorView: View) = CustomSnackBar(view, message, anchorView)
   }

   private val context = view.context
   private val snackBar = Snackbar.make(view, "", 5000)
   private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

   private val inflater = LayoutInflater.from(context)
   private val snackBarBinding : CustomSnackbarBinding =
      DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false)

   init {
      initView()
      initData()
   }

   private fun initView() {
      with(snackBarLayout) {
         removeAllViews()
         setPadding(0, 0, 0, 24)
         setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
         addView(snackBarBinding.root, 0)
      }
   }

   private fun initData() {
      snackBarBinding.message.text = message
   }

   fun show() {
      snackBar.anchorView = anchorView
      snackBar.duration = Snackbar.LENGTH_SHORT
      snackBar.show()
   }
}