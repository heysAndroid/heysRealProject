package com.hey.heys.ui.login.sign_up.age

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.SignUpAgeFragmentBinding
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
import java.time.LocalDate
import java.util.Calendar

class SignUpAgeFragment : Fragment() {
   private lateinit var binding: SignUpAgeFragmentBinding
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpAgeFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }

      binding.okButton.setOnClickListener {
         UserPreference.birthday = "${binding.npYear.value}-${binding.npMonth.value.toString().convertSingleToDoubleDigit()}-${binding.npDate.value.toString().convertSingleToDoubleDigit()}"
         UserPreference.age = getInternationalAge(binding.npYear.value)
         goToInterest()
      }

      binding.npYear.apply {
         wrapSelectorWheel = false
         descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
         minValue = 1
         maxValue = Calendar.getInstance().get(Calendar.YEAR)
         value = Calendar.getInstance().get(Calendar.YEAR)
      }

      binding.npMonth.apply {
         wrapSelectorWheel = false
         descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
         minValue = 1
         maxValue = 12
         value = Calendar.getInstance().get(Calendar.MONTH)
      }

      binding.npDate.apply {
         wrapSelectorWheel = false
         descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
         minValue = 1
         maxValue = 31
         value = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
      }
   }

   private fun getInternationalAge(year: Int): Int {
      val curYear = LocalDate.now().year
      var internationalAge = curYear - year

      if (year + internationalAge > curYear) {
         internationalAge -= 1
      }

      return internationalAge
   }

   private fun goToInterest() {
      findNavController().navigate(R.id.action_signUpAgeFragment_to_signUpInterestFragment)
   }

   fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this
}