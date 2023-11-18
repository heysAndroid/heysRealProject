package com.example.heys

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.heys.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

open class Snack(open val view: View, open val message: String, open val anchorView: View?)

class CustomSnackBar(
   override val view: View,
   override val message: String,
   override val anchorView: View?) : Snack(view, message, anchorView) {

   constructor(view: View, message: String, anchorView: View?, move: Boolean, subMessage: String = "") : this(view, message, anchorView) {
      snackBarBinding.goToNext.isVisible = move
      snackBarBinding.goToNext.text = subMessage
   }

   private val context = view.context
   private val snackBar = Snackbar.make(view, "", 5000)
   private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
   private val inflater = LayoutInflater.from(context)
   private val snackBarBinding: CustomSnackbarBinding =
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
      snackBarBinding.goToNext.isVisible = false
   }

   fun show() {
      snackBar.anchorView = anchorView
      snackBar.duration = Snackbar.LENGTH_SHORT
      snackBar.show()
   }
}