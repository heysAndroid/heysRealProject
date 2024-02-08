package com.hey.heys.ui.channel.profile

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.hey.heys.R
import com.hey.heys.databinding.UserProfileDialogBinding
import com.hey.heys.enums.Gender
import com.hey.heys.model.OtherUser

class UserProfileDialog(private val context: Context, private val user: OtherUser) : DialogFragment() {
   private lateinit var binding: UserProfileDialogBinding
   private lateinit var listener: DialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = UserProfileDialogBinding.inflate(layoutInflater)
      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setData(user)
      binding.imgClose.setOnClickListener { dismiss() }
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

   private fun setData(user: OtherUser) {
      with(binding) {
         // 소개글
         if (user.introduce.isNullOrBlank()) {
            tvIntroduce.text = "아직 소개할 내용이 없어요."
            tvIntroduce.setTextColor(ContextCompat.getColor(context, R.color.color_828282))
         } else {
            tvIntroduce.text = "\"${user.introduce}\""
            tvIntroduce.setTextColor(ContextCompat.getColor(context, R.color.color_262626))
         }

         tvName.text = user.userName

         // mbti
         if (user.userPersonality.isNullOrBlank()) {
            tvMbti.text = "????"
            tvMbti.isEnabled = false
         } else {
            tvMbti.text = user.userPersonality
            tvMbti.isEnabled = true
         }

         // 관심분야/직무
         var interestString = ""
         user.interests.forEach { interestString += "#$it " }
         tvInterestContent.text = interestString

         // 직업
         if (user.job.isNullOrBlank()) {
            tvJobContent.apply {
               text = "아직 소개할 직업이 없어요."
               setTextColor(ContextCompat.getColor(context, R.color.color_4d262626))
            }
         } else {
            tvJobContent.apply {
               text = user.job
               setTextColor(ContextCompat.getColor(context, R.color.color_262626))
            }
         }

         // 사용가능한 스킬
         if (user.capability.isNullOrBlank()) {
            tvSkillContent.apply {
               text = "아직 소개할 스킬이 없어요."
               setTextColor(ContextCompat.getColor(context, R.color.color_4d262626))
            }

         } else {
            var skillString = ""
            user.capability.split(",").forEach {
               skillString += "#$it "
            }
            tvSkillContent.apply {
               text = skillString
               setTextColor(ContextCompat.getColor(context, R.color.color_262626))
            }
         }
         setByPercent(user.percentage, user.gender)
         setProfileLink(user.profileLinks)
      }
   }

   private fun setByPercent(percentage: Int, gender: String) {
      // 프로필 이미지 설정
      when (percentage) {
         in 0..49 -> {
            when (gender) {
               Gender.Male.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_male_0)
               Gender.Female.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_female_0)
               else -> binding.imgProfile.setImageResource(R.drawable.ic_none_0)
            }
         }

         in 50..99 -> {
            when (gender) {
               Gender.Male.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_male_50)
               Gender.Female.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_female_50)
               else -> binding.imgProfile.setImageResource(R.drawable.ic_none_50)
            }
         }

         100 -> {
            when (gender) {
               Gender.Male.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_male_100)
               Gender.Female.genderEnglish -> binding.imgProfile.setImageResource(R.drawable.ic_female_100)
               else -> binding.imgProfile.setImageResource(R.drawable.ic_none_100)
            }
         }
      }
   }

   private fun setProfileLink(links: Array<String>) {
      links.forEach { link ->
         if (link.contains("kakao")) {
            binding.imgKakao.apply {
               setImageResource(R.drawable.ic_kakao_checked)
               setOnClickListener { listener.onClick(link) }
            }
         } else if (link.contains("behance")) {
            binding.imgBehance.apply {
               setImageResource(R.drawable.ic_behance_checked)
               setOnClickListener { listener.onClick(link) }
            }
         } else if (link.contains("insta")) {
            binding.imgInstagram.apply {
               setImageResource(R.drawable.ic_instagram_checked)
               setOnClickListener { listener.onClick(link) }
            }
         } else if (link.contains("github")) {
            binding.imgGithub.apply {
               setImageResource(R.drawable.ic_github_checked)
               setOnClickListener { listener.onClick(link) }
            }
         } else {
            if (!link.isNullOrBlank()) {
               binding.imgDefault.apply {
                  setImageResource(R.drawable.ic_clip_checked)
                  setOnClickListener { listener.onClick(link) }
               }
            }
         }
      }
   }

   fun onClickListener(listener: (String) -> Unit) {
      this.listener = object : DialogOnClickListener {
         override fun onClick(url: String) {
            listener(url)
         }
      }
   }

   interface DialogOnClickListener {
      fun onClick(url: String)
   }
}