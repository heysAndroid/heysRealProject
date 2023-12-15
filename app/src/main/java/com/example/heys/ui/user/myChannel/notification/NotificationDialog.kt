package com.example.heys.ui.user.myChannel.notification

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import androidx.core.text.bold
import androidx.fragment.app.DialogFragment
import com.example.heys.databinding.NotificationDialogBinding
import com.example.heys.model.network.Notification

class NotificationDialog(private val context: Context, private val noti: Notification) : DialogFragment() {
   private lateinit var binding: NotificationDialogBinding
   private lateinit var listener: DialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = NotificationDialogBinding.inflate(layoutInflater)
      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setData(noti)
      binding.btnClose.setOnClickListener { dismiss() }
   }

   override fun onResume() {
      super.onResume()
      val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
      val display = windowManager.defaultDisplay
      val size = Point()
      display.getSize(size)

      val params = dialog?.window?.attributes
      val deviceWidth = size.x
      params?.width = (deviceWidth * 0.9).toInt()
      dialog?.window?.attributes = params as LayoutParams
   }

   private fun setData(noti: Notification) {
      with(binding) {
         tvTitle.text = noti.title
         tvDate.text = noti.createdAt
         val sub = noti.content.split("[")
         var bold = arrayListOf<String>()
         var simple = arrayListOf<String>()
         var content = SpannableStringBuilder()

         sub.forEach {
            if (it.contains("]")) {
               val ss = it.split("]")
               bold.add(ss[0])
               simple.add(ss[1])
            }
         }

         for (i in 0 until bold.count()) {
            content.bold { append(bold[i]) }.append(simple[i])
         }
         tvContent.text = content

         if (noti.content.contains("승인요청을 수락했어요")) {
            btnChannel.visibility = View.VISIBLE
            btnChannel.setOnClickListener { listener.onClick(noti.channelId) }
         } else {
            btnChannel.visibility = View.GONE
         }
      }
   }

   fun onClickListener(listener: (Int) -> Unit) {
      this.listener = object : DialogOnClickListener {
         override fun onClick(id: Int) {
            listener(id)
         }
      }
   }

   interface DialogOnClickListener {
      fun onClick(id: Int)
   }
}