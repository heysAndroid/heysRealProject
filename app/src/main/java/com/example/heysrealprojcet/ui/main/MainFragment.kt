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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.MarginItemDecoration
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainFragmentBinding
import com.example.heysrealprojcet.model.ContestType
import com.example.heysrealprojcet.model.ExtracurricularType
import com.example.heysrealprojcet.ui.main.category.CategoryRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular.ExtracurricularInterestItemRecyclerViewAdapter
import com.example.heysrealprojcet.ui.sign_up.profileCard.SignUpProfileCardBottomSheet
import com.example.heysrealprojcet.util.UserPreference

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding
   private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var extracurricularRecyclerViewAdapter: ExtracurricularInterestItemRecyclerViewAdapter
   private lateinit var contestList: MutableList<ContestType>
   private lateinit var extracurricularList: MutableList<ExtracurricularType>
   private var position = 0

   val args: MainFragmentArgs by navArgs()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

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

      // 회원가입에서 넘어온 경우 프로필 카드 보여주기
      if (args.isNewUser) {
         val bottomSheet = SignUpProfileCardBottomSheet(UserPreference.name) { goToProfileEdit() }
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }

      makeExtracurricularList()
      makeContestList()

      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(list = contestList) { goToContest() }
      extracurricularRecyclerViewAdapter = ExtracurricularInterestItemRecyclerViewAdapter(list = extracurricularList) { goToActivity() }

      binding.contestList.adapter = categoryRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      binding.contestList.addItemDecoration(
         MarginItemDecoration(
            resources.getDimension(R.dimen.contestList_item_margin).toInt(), contestList.lastIndex))

      binding.extracurricularList.adapter = extracurricularRecyclerViewAdapter
      binding.extracurricularList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      binding.extracurricularList.addItemDecoration(
         MarginItemDecoration(
            resources.getDimension(R.dimen.activityList_item_margin).toInt(), extracurricularList.lastIndex))

      with(binding) {
         contestAllText.setOnClickListener { goToContest() }
         activityAllText.setOnClickListener { goToActivity() }
         studyContainer.setOnClickListener { goToStudy() }
      }
      Log.d("=== accessToken ===", UserPreference.accessToken)

      val onScrollListener = object : RecyclerView.OnScrollListener() {
         override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisibleItemPosition =
               (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

            if (lastVisibleItemPosition != -1) {
               contestList[position].bool = false
               position = lastVisibleItemPosition
               contestList[lastVisibleItemPosition].bool = true
               categoryRecyclerViewAdapter.notifyDataSetChanged()
            }
         }
      }
      binding.contestList.setOnScrollListener(onScrollListener)
   }

   private fun changeFragment(fragment: Fragment) {
      requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
   }

   private fun makeExtracurricularList() {
      extracurricularList = mutableListOf(
         ExtracurricularType("취향저격", "내 관심분야별", R.drawable.ic_interested),
         ExtracurricularType("서둘러요!", "마감 임박!", R.drawable.ic_hurry),
         ExtracurricularType("너도나도", "많이 찾는", R.drawable.ic_finding),
         ExtracurricularType("어디보자", "새로 열린", R.drawable.ic_new))
   }

   private fun makeContestList() {
      contestList = mutableListOf(
         ContestType("관심 \n분야별", "관심분야 #개발과 \n관련있는 공모전이에요!", R.drawable.ic_drawing_board1, true),
         ContestType("마감 \n임박!", "서두르세요! \n곧 마감하는 공모전이에요!", R.drawable.ic_drawing_board2, false),
         ContestType("너도나도 \n많이 찾는", "와글와글 \n많이찾는 공모전들이에요!", R.drawable.ic_drawing_board3, false),
         ContestType("어디보자 \n새로 열린", "새로운게 뭐가있나~ \n새로열린 공모전들이에요!", R.drawable.ic_drawing_board4, false))
   }

   private fun goToStudy() {
      findNavController().navigate(R.id.action_mainFragment_to_channelNameFragment)
   }

   private fun goToContest() {
      findNavController().navigate(R.id.action_mainFragment_to_contestFragment)
   }

   private fun goToActivity() {
      findNavController().navigate(R.id.action_mainFragment_to_extracurricularListFragment)
   }

   private fun goToProfileEdit() {
      findNavController().navigate(R.id.action_mainFragment_to_profileEditFragment)
   }
}

