package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.contestActivity.contest.activityRecyclerViewAdapter
import com.example.heysrealprojcet.util.UserPreference

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding
   private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var activityRecyclerViewAdapter: activityRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val mWindow = requireActivity().window
      mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      makeList()
      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(type = typeList) { goToJoin() }
      activityRecyclerViewAdapter = activityRecyclerViewAdapter(type = typeList) { goToJoin() }

      binding.contestList.adapter = categoryRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      binding.activityList.adapter = activityRecyclerViewAdapter
      binding.activityList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      // 로그인 안하면 회원가입 팝업 띄우도록
      if (isLogin()) {
         binding.studyContainer.setOnClickListener { goToStudy() }
      } else {
         with(binding) {
            contestAllText.setOnClickListener { goToContest() }
            activityAllText.setOnClickListener { goToActivity() }
            studyAllText.setOnClickListener { goToJoin() }
            studyContainer.setOnClickListener { goToStudy() }
         }
      }
      Log.d("=== accessToken ===", UserPreference.accessToken)
   }

   private fun changeFragment(fragment: Fragment) {
      requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
   }

   private fun makeList() {
      typeList = mutableListOf("관심분야별", "마감 임박", "많이 찾는", "새로 열린")
   }

   private fun goToJoin() {
      findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
   }

   private fun goToStudy() {
      findNavController().navigate(R.id.action_mainFragment_to_studyFragment)
   }

   private fun goToContest() {
      findNavController().navigate(R.id.action_mainFragment_to_contestFragment)
   }

   private fun goToActivity() {
      findNavController().navigate(R.id.action_mainFragment_to_activityListFragment)
   }

   private fun isLogin(): Boolean = UserPreference.accessToken.isNotEmpty()
}