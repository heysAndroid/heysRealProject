package com.example.heysrealprojcet.ui.main.content.study.hey.waiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.WaitingHeyListFragmentBinding
import com.example.heysrealprojcet.model.UserProfile
import com.example.heysrealprojcet.ui.main.MainActivity

class WaitingHeyListFragment : Fragment() {
   private lateinit var binding: WaitingHeyListFragmentBinding
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
      binding = WaitingHeyListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      makeList()
      waitingHeyItemRecyclerViewAdapter = WaitingHeyItemRecyclerViewAdapter(userProfile = userList) {  }
      binding.waitingHeysList.adapter = waitingHeyItemRecyclerViewAdapter
      binding.waitingHeysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      userList = mutableListOf(
         UserProfile("이름1", listOf("IT", "기획", "UX"), "대학생", listOf("user research", "figma", "ms office")),
         UserProfile("이름2", listOf("IT", "개발", "안드로이드"), "대학생", listOf("Android Studio", "Kotlin", "Java")),
         UserProfile("이름3", listOf("웹", "프로그래밍"), "직장인", listOf("JavaScript", "React", "Bootstrap")),
         UserProfile("이름4", listOf("프로그래밍", "데이터", "통계"), "취준생", listOf("pandas", "AI", "Machine Learning")),
         UserProfile("이름5", listOf("개발", "어플", "iOS"), "취준생", listOf("Swift", "iOS", "XCode")))
   }
}