package com.example.heysrealprojcet.ui.channel.dialog.period

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelPeriodDialogBinding
import com.example.heysrealprojcet.util.ChannelPreference
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class ChannelPeriodDialog : DialogFragment() {
   private lateinit var binding: ChannelPeriodDialogBinding
   private val viewModel by viewModels<ChannelPeriodDialogViewModel>()
   private lateinit var listener: ChannelPeriodDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPeriodDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)

      binding.cafeteriaCalendar.itemAnimator = null
      binding.cafeteriaCalendar.setup(
         YearMonth.now().minusMonths(0),
         YearMonth.now().plusMonths(2),
         WeekFields.of(Locale.getDefault()).firstDayOfWeek
      )
      binding.cafeteriaCalendar.scrollToMonth(YearMonth.now())

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
         override fun create(view: View): CafeteriaContainer =
            CafeteriaContainer(view, binding.cafeteriaCalendar)

         override fun bind(container: CafeteriaContainer, day: CalendarDay) {
            container.bind(day)
         }
      }

      binding.btnSave.setOnClickListener {
         // 채널 정보 fragment 로 선택값 전달
         listener.onClick(ChannelPreference.channelRecruitPeriod)
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }


      return binding.root
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelPeriodDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelPeriodDialogOnClickListener {
      fun onClick(content: String)
   }
}