package com.example.heysrealprojcet.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.MarginItemDecoration
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainFragmentBinding
import com.example.heysrealprojcet.model.ContestType
import com.example.heysrealprojcet.model.ExtracurricularType
import com.example.heysrealprojcet.model.network.MyPage
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.category.CategoryRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular.ExtracurricularInterestItemRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.profileCard.SignUpProfileCardBottomSheet
import com.example.heysrealprojcet.util.UserPreference
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainFragment : Fragment() {
   private lateinit var mainActivity: MainActivity
   private lateinit var binding: MainFragmentBinding
   val viewModel by viewModels<MainViewModel>()
   val args: MainFragmentArgs by navArgs()

   private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var extracurricularRecyclerViewAdapter: ExtracurricularInterestItemRecyclerViewAdapter

   private lateinit var contestList: MutableList<ContestType>
   private lateinit var extracurricularList: MutableList<ExtracurricularType>
   private lateinit var interestList: MutableList<String>
   private lateinit var callback: OnBackPressedCallback

   private var position = 0

   companion object {
      const val MY_INTEREST_LIST = "myInterestList"
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onAttach(context: Context) {
      super.onAttach(context)
      callback = object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
            if (navHostFragment.childFragmentManager.primaryNavigationFragment is MainFragment) {
               ActivityCompat.finishAffinity(requireActivity())
               exitProcess(0)
            }
         }
      }
      requireActivity().onBackPressedDispatcher.addCallback(this, callback)
   }

   override fun onDetach() {
      super.onDetach()
      callback.remove()
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      // 회원가입에서 넘어온 경우 프로필 카드 보여주기
      if (args.isNewUser) {
         val bottomSheet = SignUpProfileCardBottomSheet(UserPreference.name) { goToMyPage() }
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }
      getMyInfo()
      makeContestList()
      makeExtracurricularList()

      // 대외활동
      extracurricularRecyclerViewAdapter = ExtracurricularInterestItemRecyclerViewAdapter(list = extracurricularList) { goToActivity() }
      binding.extracurricularList.apply {
         adapter = extracurricularRecyclerViewAdapter
         layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         addItemDecoration(
            MarginItemDecoration(
               resources.getDimension(R.dimen.activityList_item_margin).toInt(), extracurricularList.lastIndex))
         setHasFixedSize(true)
      }

      with(binding) {
         contestAllText.setOnClickListener { goToContest(type = "all") }
         activityAllText.setOnClickListener { goToActivity() }
         studyCreate.setOnClickListener { goToStudyCreate() }
         studyList.setOnClickListener { goToStudyList() }
      }

      binding.contestList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         @SuppressLint("NotifyDataSetChanged")
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
      })

      FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
         if (!task.isSuccessful) {
            Log.w("FCM registration failed", task.exception)
            return@OnCompleteListener
         }

         val token = task.result
         Log.d("FCM test", token)
      })
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
         ContestType("관심 \n분야별", R.drawable.ic_drawing_board1, true),
         ContestType("마감 \n임박!", R.drawable.ic_drawing_board2, false),
         ContestType("너도나도 \n많이 찾는", R.drawable.ic_drawing_board3, false),
         ContestType("어디보자 \n새로 열린", R.drawable.ic_drawing_board4, false))
   }

   private fun goToStudyCreate() {
      findNavController().navigate(R.id.action_mainFragment_to_channelNameFragment)
   }

   private fun goToContest(type: String = "default") {
      findNavController().navigate(R.id.action_mainFragment_to_contestFragment, bundleOf("type" to type))
   }

   private fun goToActivity() {
      findNavController().navigate(R.id.action_mainFragment_to_extracurricularListFragment)
   }

   private fun goToMyPage() {
      mainActivity.goToMyPage()
   }

   private fun goToStudyList() {
      findNavController().navigate(R.id.action_mainFragment_to_studyFragment)
   }

   private fun setInterestRecyclerView() {
      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(
         list = contestList,
         myInterestList = UserPreference.interests.split(",").toMutableList()
      ) { goToContest(type = "interest") }

      binding.contestList.apply {
         adapter = categoryRecyclerViewAdapter
         layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.contestList_item_margin).toInt(), contestList.lastIndex))
         setHasFixedSize(true)
      }
   }

   private fun getMyInfo() {
      val token = UserPreference.accessToken
      viewModel.getMyInfo("Bearer $token").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.w("getMyInfo: ", "success")
               response.data?.user?.let { setUserPreference(it) }
               setInterestRecyclerView()
            }

            is NetworkResult.Loading -> {
               Log.w("getMyInfo: ", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getMyInfo error: ", response.message.toString())
               goToLogin()
            }
         }
      }
   }

   private fun goToLogin() {
      val intent = Intent(requireContext(), MainActivity::class.java).apply {
         putExtra(Intent.EXTRA_TEXT, "login")
         type = "text/plain"
      }
      startActivity(intent)
      requireActivity().finish()
   }

   private fun setUserPreference(user: MyPage) {
      UserPreference.interests = user.interests.joinToString(separator = ",")
      UserPreference.name = user.name
      UserPreference.phoneNumber = user.phone
      UserPreference.gender = user.gender
      UserPreference.birthday = user.birthDate
      UserPreference.job = user.job
      UserPreference.introduce = user.introduce
      UserPreference.skill = user.capability
      UserPreference.mbti = user.userPersonality ?: ""
      UserPreference.percentage = user.percentage
      UserPreference.joinChannelCount = user.joinChannelCount
      UserPreference.waitingChannelCount = user.waitingChannelCount
   }
}

