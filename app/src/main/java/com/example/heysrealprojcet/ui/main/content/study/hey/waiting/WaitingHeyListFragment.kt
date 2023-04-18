package com.example.heysrealprojcet.ui.main.content.study.hey.waiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.WaitingUserListFragmentBinding
import com.example.heysrealprojcet.model.UserProfile
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.user.channel.waitingChannel.ChannelCancelBottomSheet

class WaitingHeyListFragment : Fragment() {
   private lateinit var binding: WaitingUserListFragmentBinding
   private lateinit var waitingHeyItemRecyclerViewAdapter: WaitingHeyItemRecyclerViewAdapter
   private lateinit var userList: MutableList<UserProfile>

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = WaitingUserListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      makeList()
      waitingHeyItemRecyclerViewAdapter = WaitingHeyItemRecyclerViewAdapter(userProfile = userList) {
         // 클릭 리스너 전달
         val bottomSheet = ChannelCancelBottomSheet()
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }
      binding.waitingUserList.adapter = waitingHeyItemRecyclerViewAdapter
      binding.waitingUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      userList = mutableListOf(
         UserProfile("이름1", listOf("IT", "기획", "UX"), "대학생", listOf("user research", "figma", "ms office"), imageResId = R.drawable.ic_user_profile1),
         UserProfile("이름2", listOf("IT", "개발", "안드로이드"), "대학생", listOf("Android Studio", "Kotlin", "Java"), imageResId = R.drawable.ic_user_profile2),
         UserProfile("이름3", listOf("웹", "프로그래밍"), "직장인", listOf("JavaScript", "React", "Bootstrap"), imageResId = R.drawable.ic_user_profile1),
         UserProfile("이름4", listOf("프로그래밍", "데이터", "통계"), "취준생", listOf("pandas", "AI", "Machine Learning"), imageResId = R.drawable.ic_user_profile2),
         UserProfile("이름5", listOf("개발", "어플", "iOS"), "취준생", listOf("Swift", "iOS", "XCode"), imageResId = R.drawable.ic_user_profile2))
   }
}