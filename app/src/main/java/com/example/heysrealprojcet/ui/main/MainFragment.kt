package com.example.heysrealprojcet.ui.main

import android.graphics.Color
import android.os.Build
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
import com.example.heysrealprojcet.model.ContestType
import com.example.heysrealprojcet.model.ExtracurricularType
import com.example.heysrealprojcet.ui.main.category.CategoryRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.contestActivity.contest.activityRecyclerViewAdapter
import com.example.heysrealprojcet.util.UserPreference

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding
   private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var activityRecyclerViewAdapter: activityRecyclerViewAdapter
   private lateinit var contestList: MutableList<ContestType>
   private lateinit var activityList: MutableList<ExtracurricularType>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val mWindow = requireActivity().window
      mWindow.apply {
         var defaultStatusBarColor: Int

         clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            defaultStatusBarColor = Color.parseColor("#FFFFFF")
            decorView.systemUiVisibility += View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
         } else {
            defaultStatusBarColor = Color.parseColor("#FFFFFF")
         }
         statusBarColor = defaultStatusBarColor
      }
      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      makeActivityList()
      makeContestList()

      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(list = contestList) { goToSignUp() }
      activityRecyclerViewAdapter = activityRecyclerViewAdapter(list = activityList) { goToSignUp() }

      binding.contestList.adapter = categoryRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      binding.activityList.adapter = activityRecyclerViewAdapter
      binding.activityList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      
      with(binding) {
         contestAllText.setOnClickListener { goToContest() }
         activityAllText.setOnClickListener { goToActivity() }
         studyAllText.setOnClickListener { goToSignUp() }
         studyContainer.setOnClickListener { goToStudy() }
      }
      Log.d("=== accessToken ===", UserPreference.accessToken)
   }

   private fun changeFragment(fragment: Fragment) {
      requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
   }

   private fun makeActivityList() {
      activityList = mutableListOf(
         ExtracurricularType("취향저격", "관심분야별", R.drawable.ic_interested),
         ExtracurricularType("서둘러요!", "마감임박", R.drawable.ic_hurry),
         ExtracurricularType("너도나도", "많이 찾는", R.drawable.ic_finding),
         ExtracurricularType("어디보자", "새로 열린", R.drawable.ic_finding_with_glasses))
   }

   private fun makeContestList() {
      contestList = mutableListOf(
         ContestType("관심 \n분야별", "관심분야 #개발과 \n관련있는 공모전이에요!", R.drawable.ic_heart),
         ContestType("마감 \n임박!", "서두르세요! \n곧 마감하는 공모전이에요!", R.drawable.ic_character_passion_2),
         ContestType("너도나도 \n많이 찾는", "와글와글 \n많이찾는 공모전들이에요!", R.drawable.ic_drawing_board),
         ContestType("어디보자 \n새로 열린", "새로운게 뭐가있나~ \n새로열린 공모전들이에요!", R.drawable.ic_shining))
   }

   private fun goToSignUp() {
      findNavController().navigate(R.id.action_mainFragment_to_signUpPopupFragment)
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