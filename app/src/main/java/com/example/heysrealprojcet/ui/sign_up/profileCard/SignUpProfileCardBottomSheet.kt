package com.example.heysrealprojcet.ui.sign_up.profileCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpProfileCardBottomSheetBinding
import com.example.heysrealprojcet.model.ProfileCardDescription
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpProfileCardBottomSheet(private val name: String) : BottomSheetDialogFragment() {
   private lateinit var binding: SignUpProfileCardBottomSheetBinding
   private lateinit var profileCardPagerAdapter: ProfileCardPagerAdapter
   private lateinit var profileList: MutableList<ProfileCardDescription>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SignUpProfileCardBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
      val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
      behavior.state = BottomSheetBehavior.STATE_EXPANDED

      setProfileList()
      profileCardPagerAdapter = ProfileCardPagerAdapter()
      profileCardPagerAdapter.submitList(profileList)

      binding.viewpager.adapter = profileCardPagerAdapter
      binding.indicator.setViewPager(binding.viewpager)
      binding.name.text = "$name 님을 어필할 수 있는\n프로필 카드를 만들었어요!"
   }

   private fun setProfileList() {
      profileList = mutableListOf(
         ProfileCardDescription("프로필 카드 완성도에 따라서 프로필이 다르게 보여져요!", resources.getDrawable(R.drawable.ic_profile_card1)),
         ProfileCardDescription("[자기소개]를 작성하면 새싹이 자라나요!", resources.getDrawable(R.drawable.ic_profile_card2)),
         ProfileCardDescription("프로필 카드를 완성하면 꽃이 피어요!", resources.getDrawable(R.drawable.ic_profile_card3)),
      )
   }
}