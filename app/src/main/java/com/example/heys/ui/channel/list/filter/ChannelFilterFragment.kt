package com.example.heys.ui.channel.list.filter

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.heys.EventObserver
import com.example.heys.R
import com.example.heys.databinding.ChannelFilterFragmentBinding
import com.example.heys.enums.ChannelForm
import com.example.heys.enums.ChannelInterest
import com.example.heys.enums.ChannelPurpose
import com.example.heys.enums.ChannelRegion
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class ChannelFilterFragment : Fragment() {
   private lateinit var binding: ChannelFilterFragmentBinding
   lateinit var viewModel: ChannelFilterViewModel

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ChannelFilterFragmentBinding.inflate(inflater, container, false)
      viewModel = ViewModelProvider(requireActivity())[ChannelFilterViewModel::class.java]
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnClose.setOnClickListener { findNavController().navigateUp() }

      // 달력
      binding.cafeteriaCalendar.apply {
         stopScroll()
         isNestedScrollingEnabled = false
         itemAnimator = null
         setup(YearMonth.now(), YearMonth.now(), WeekFields.of(Locale.getDefault()).firstDayOfWeek)
         dayBinder = object : DayBinder<ChannelFilterCafeteriaContainer> {
            override fun bind(container: ChannelFilterCafeteriaContainer, day: CalendarDay) = container.bind(day)

            override fun create(view: View): ChannelFilterCafeteriaContainer = ChannelFilterCafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

         }
      }

      viewModel.isCalendarInit.observe(viewLifecycleOwner, EventObserver {
         if (it) binding.cafeteriaCalendar.notifyCalendarChanged()
      })

      viewModel.calendarPosition.observe(viewLifecycleOwner) {
         if (currentTime.year == YearMonth.now().year && it == YearMonth.now().month.value) {
            binding.calendarBack.visibility = View.INVISIBLE
         } else {
            binding.calendarBack.visibility = View.VISIBLE
         }
      }

      viewModel.calendarDate.observe(viewLifecycleOwner) {
         if (viewModel.selectedDate != null) {
            binding.tvDate.apply {
               text = it.format(selectedFormatter)
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_53c740))
               setTypeface(null, Typeface.BOLD)
            }
         } else {
            binding.tvDate.apply {
               text = "선택해주세요"
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
               setTypeface(null, Typeface.NORMAL)
            }
         }
      }

      binding.yearMonth.text = currentTime.format(calendarFormatter)
      setCalendarBack()
      setCalendarForward()

      binding.btnApply.setOnClickListener { findNavController().navigateUp() }

      // 재설정
      binding.btnInit.setOnClickListener {
         viewModel.init()

         unselectAllInterestButton()
         unselectAllFormButton()
         binding.rgRegion.visibility = View.GONE
         unselectAllPurposeButton()
      }

      // 활동 형태
      viewModel.selectedForm.observe(viewLifecycleOwner) {
         unselectAllFormButton()

         when (it) {
            ChannelForm.Both.engForm -> {
               selectButton(binding.btnOnoffline)
               binding.rgRegion.visibility = View.GONE
            }

            ChannelForm.Offline.engForm -> {
               selectButton(binding.btnOffline)

               unselectAllRegionButton()
               selectButton(binding.btnWhole)
               viewModel.setDefaultRegion()

               binding.rgRegion.visibility = View.VISIBLE
            }

            ChannelForm.Online.engForm -> {
               selectButton(binding.btnOnline)
               binding.rgRegion.visibility = View.GONE
            }
         }
      }

      // 활동 지역
      viewModel.selectedRegion.observe(viewLifecycleOwner) {
         unselectAllRegionButton()
         when (it) {
            ChannelRegion.Whole.region -> selectButton(binding.btnWhole)
            ChannelRegion.Seoul.region -> selectButton(binding.btnSeoul)
            ChannelRegion.Gyeonggi.region -> selectButton(binding.btnGyeonggi)
            ChannelRegion.Incheon.region -> selectButton(binding.btnIncheon)
            ChannelRegion.Gangwon.region -> selectButton(binding.btnGangwon)
            ChannelRegion.Chungcheong.region -> selectButton(binding.btnChungcheong)
            ChannelRegion.Jeolla.region -> selectButton(binding.btnJeolla)
            ChannelRegion.Gyeongsang.region -> selectButton(binding.btnGyeongsang)
            ChannelRegion.Jeju.region -> selectButton(binding.btnJeju)
         }
      }

      // 활동 목적
      viewModel.selectedPurpose.observe(viewLifecycleOwner) { array ->
         unselectAllPurposeButton()
         array.forEach {
            when (it) {
               ChannelPurpose.Capability.purpose -> selectButton(binding.btnCapability)
               ChannelPurpose.Networking.purpose -> selectButton(binding.btnNetworking)
               ChannelPurpose.JobSeeking.purpose -> selectButton(binding.btnJobSeeking)
               ChannelPurpose.Skill.purpose -> selectButton(binding.btnSkill)
               ChannelPurpose.Experience.purpose -> selectButton(binding.btnExperience)
               ChannelPurpose.Portfolio.purpose -> selectButton(binding.btnPortfolio)
            }
         }
      }

      viewModel.selectedInterest.observe(viewLifecycleOwner) { array ->
         unselectAllInterestButton()
         array.forEach {
            Log.w("interest:: ", it)
            when (it) {
               ChannelInterest.Planning.interest -> selectButton(binding.btnPlanning)
               ChannelInterest.Design.interest -> selectButton(binding.btnDesign)
               ChannelInterest.Programming.interest -> selectButton(binding.btnProgramming)
               ChannelInterest.IT.interest -> selectButton(binding.btnIT)
               ChannelInterest.Data.interest -> selectButton(binding.btnData)
               ChannelInterest.Game.interest -> selectButton(binding.btnGame)
               ChannelInterest.Marketing.interest -> selectButton(binding.btnMarketing)
               ChannelInterest.Business.interest -> selectButton(binding.btnBusiness)
               ChannelInterest.Economics.interest -> selectButton(binding.btnEconomics)
               ChannelInterest.Engineering.interest -> selectButton(binding.btnEngineering)
               ChannelInterest.Art.interest -> selectButton(binding.btnArt)
               ChannelInterest.Novel.interest -> selectButton(binding.btnNovel)
               ChannelInterest.Lifestyle.interest -> selectButton(binding.btnLifestyle)
               ChannelInterest.Picture.interest -> selectButton(binding.btnPicture)
               ChannelInterest.Culture.interest -> selectButton(binding.btnCulture)
               ChannelInterest.Travel.interest -> selectButton(binding.btnTravel)
               ChannelInterest.Environment.interest -> selectButton(binding.btnEnvironment)
               ChannelInterest.Language.interest -> selectButton(binding.btnLanguage)
               ChannelInterest.MediaContents.interest -> selectButton(binding.btnMediaContents)
               ChannelInterest.Paper.interest -> selectButton(binding.btnPaper)
               ChannelInterest.Sports.interest -> selectButton(binding.btnSports)
               ChannelInterest.Dance.interest -> selectButton(binding.btnDance)
               ChannelInterest.Service.interest -> selectButton(binding.btnPublic)
            }
         }
      }
   }

   private fun setCalendarBack() {
      binding.calendarBack.setOnClickListener {
         if (currentTime.year > YearMonth.now().year) {
            if (viewModel.calendarPosition.value == 1) {
               viewModel.setPosition(12)
               currentTime = currentTime.minusYears(1)
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
            } else {
               viewModel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            }
         } else {
            if (currentTime.year == YearMonth.now().year &&
               viewModel.calendarPosition.value!! > YearMonth.now().month.value
            ) {
               viewModel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            }
         }
         binding.yearMonth.text = currentTime.format(calendarFormatter)
      }
   }

   private fun setCalendarForward() {
      binding.calendarForward.setOnClickListener {
         if (viewModel.calendarPosition.value in 1..11) {
            currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            viewModel.plusPosition()
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
            currentTime = currentTime.plusMonths(1)
         } else {
            viewModel.setPosition(1)
            currentTime = currentTime.plusYears(1)
            currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
         }
         binding.yearMonth.text = currentTime.format(calendarFormatter)
      }
   }

   private fun unselectButton(view: View) {
      val button = view as Button
      button.isSelected = false
      button.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectButton(view: View) {
      val button = view as Button
      button.isSelected = true
      button.setTypeface(null, Typeface.BOLD)
   }

   private fun unselectAllFormButton() {
      unselectButton(binding.btnOnoffline)
      unselectButton(binding.btnOnline)
      unselectButton(binding.btnOffline)
   }

   private fun unselectAllRegionButton() {
      unselectButton(binding.btnWhole)
      unselectButton(binding.btnSeoul)
      unselectButton(binding.btnGyeonggi)
      unselectButton(binding.btnIncheon)
      unselectButton(binding.btnGangwon)
      unselectButton(binding.btnChungcheong)
      unselectButton(binding.btnJeolla)
      unselectButton(binding.btnGyeongsang)
      unselectButton(binding.btnJeju)
   }

   private fun unselectAllPurposeButton() {
      unselectButton(binding.btnCapability)
      unselectButton(binding.btnNetworking)
      unselectButton(binding.btnJobSeeking)
      unselectButton(binding.btnSkill)
      unselectButton(binding.btnExperience)
      unselectButton(binding.btnPortfolio)
   }

   private fun unselectAllInterestButton() {
      unselectButton(binding.btnPlanning)
      unselectButton(binding.btnDesign)
      unselectButton(binding.btnProgramming)
      unselectButton(binding.btnIT)
      unselectButton(binding.btnData)
      unselectButton(binding.btnGame)
      unselectButton(binding.btnMarketing)
      unselectButton(binding.btnBusiness)
      unselectButton(binding.btnEconomics)
      unselectButton(binding.btnEngineering)
      unselectButton(binding.btnArt)
      unselectButton(binding.btnNovel)
      unselectButton(binding.btnLifestyle)
      unselectButton(binding.btnPicture)
      unselectButton(binding.btnCulture)
      unselectButton(binding.btnTravel)
      unselectButton(binding.btnEnvironment)
      unselectButton(binding.btnLanguage)
      unselectButton(binding.btnMediaContents)
      unselectButton(binding.btnPaper)
      unselectButton(binding.btnSports)
      unselectButton(binding.btnDance)
      unselectButton(binding.btnPublic)
   }
}