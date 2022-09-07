package com.example.heysrealprojcet.ui.channel.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.heysrealprojcet.databinding.ProfileDialogBinding
import com.example.heysrealprojcet.model.UserProfile

class ProfileDialog(context: Context, private val userProfile: UserProfile, private val okClickListener: () -> Unit) : Dialog(context) {
   private lateinit var binding: ProfileDialogBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ProfileDialogBinding.inflate(layoutInflater)
      setContentView(binding.root)
      initView()
   }

   private fun initView() {
      with(binding) {
         setCancelable(false)
         // 배경 투명하게
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         setData(userProfile)
         closeButton.setOnClickListener { dismiss() }
         chatButton.setOnClickListener {
            okClickListener()
            dismiss()
         }
      }
   }

   private fun setData(userProfile: UserProfile) {
      with(binding) {
         name.text = userProfile.name

         var interestString = ""
         userProfile.interest.forEach {
            interestString += "#${it} "
         }
         interestContent.text = interestString

         jobContent.text = userProfile.job

         var skillString = ""
         userProfile.skill.forEachIndexed { index, skill ->
            // 마지막 아이템에는 컴마 안붙이게
            skillString += if (index == userProfile.skill.lastIndex) {
               skill
            } else {
               "${skill}, "
            }
         }
         skillContent.text = skillString
      }
   }
}