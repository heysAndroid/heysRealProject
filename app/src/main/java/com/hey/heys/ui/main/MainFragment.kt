package com.hey.heys.ui.main

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
import com.hey.heys.MarginItemDecoration
import com.hey.heys.R
import com.hey.heys.databinding.MainFragmentBinding
import com.hey.heys.enums.ChannelType
import com.hey.heys.enums.ContentOrder
import com.hey.heys.model.ContestType
import com.hey.heys.model.ExtracurricularType
import com.hey.heys.model.network.MyPage
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.channel.profile.UserProfileDialog
import com.hey.heys.ui.main.category.CategoryRecyclerViewAdapter
import com.hey.heys.ui.main.content.contestExtracurricular.extracurricular.ExtracurricularInterestItemRecyclerViewAdapter
import com.hey.heys.ui.main.profileCard.SignUpProfileCardBottomSheet
import com.hey.heys.util.UserPreference
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

      val intent = requireActivity().intent.getStringExtra(Intent.EXTRA_TEXT)
      if (intent?.contains("profile") == true) {
         val profileId = intent?.replace("profile", "")?.toInt()
         profileId?.let { showUserProfile(it) }
      }

      if (intent?.contains("channel") == true) {
         val channelId = intent?.replace("channel", "")?.toInt()
         channelId?.let { goToChannel(channelId) }
         requireActivity().intent.putExtra(Intent.EXTRA_TEXT, "")
      }

      if (intent?.contains("content") == true) {
         val contentId = intent?.replace("content", "")?.toInt()
         contentId?.let { goToContent(contentId) }
         // 뒤로가기 했을 때 intent clear
         requireActivity().intent.putExtra(Intent.EXTRA_TEXT, "")
      }

      getMyInfo()
      makeContestList()
      makeExtracurricularList()
      setExtraCurricular()

      with(binding) {
         tvContestAll.setOnClickListener { goToContest() }
         tvExtracurricularAll.setOnClickListener { goToExtraCurricular() }
         btnCreateStudy.setOnClickListener { goToStudyCreate() }
         btnStudyList.setOnClickListener { goToStudyList() }
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
   }

   private fun makeExtracurricularList() {
      extracurricularList = mutableListOf(
         ExtracurricularType("취향저격", "내 관심분야별", R.drawable.ic_interested, ContentOrder.Interest.order),
         ExtracurricularType("서둘러요!", "마감 임박!", R.drawable.ic_hurry, ContentOrder.Dday.order),
         ExtracurricularType("너도나도", "많이 찾는", R.drawable.ic_finding, ContentOrder.Popular.order),
         ExtracurricularType("어디보자", "새로 열린", R.drawable.ic_new))
   }

   private fun makeContestList() {
      contestList = mutableListOf(
         ContestType("관심분야별", R.drawable.ic_drawing_board1, true, ContentOrder.Interest.order),
         ContestType("마감임박!", R.drawable.ic_drawing_board2, false, ContentOrder.Dday.order),
         ContestType("너도나도 \n많이 찾는", R.drawable.ic_drawing_board3, false, ContentOrder.Popular.order),
         ContestType("어디보자 \n새로 열린", R.drawable.ic_drawing_board4, false))
   }

   private fun goToStudyCreate() {
      findNavController().navigate(
         R.id.action_mainFragment_to_channelNameFragment,
         bundleOf("channelType" to ChannelType.Study.typeEng))
   }

   private fun goToContest(type: String = "Default") {
      findNavController().navigate(R.id.action_mainFragment_to_contestFragment, bundleOf("type" to type))
   }

   private fun goToExtraCurricular(type: String = "Default") {
      findNavController().navigate(R.id.action_mainFragment_to_extracurricularListFragment, bundleOf("type" to type))
   }

   private fun goToMyPage() {
      mainActivity.goToMyPage()
   }

   private fun goToStudyList() {
      findNavController().navigate(R.id.action_mainFragment_to_studyFragment)
   }

   private fun goToChannel(id: Int) {
      findNavController().navigate(R.id.action_mainFragment_to_channelDetailFragment, bundleOf("channelId" to id))
   }

   private fun goToContent(id: Int) {
      findNavController().navigate(
         R.id.action_mainFragment_to_contestExtracurricularDetailFragment,
         bundleOf("channelType" to ChannelType.Contest.typeEng, "contentId" to id))
   }

   private fun setContest() {
      var myList = mutableListOf("")
      if (!UserPreference.interests.isNullOrBlank()) {
         myList = UserPreference.interests.split(",").toMutableList()
      }

      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(
         list = contestList,
         myInterestList = myList
      ) { goToContest(type = it) }

      binding.contestList.apply {
         adapter = categoryRecyclerViewAdapter
         layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         addItemDecoration(
            MarginItemDecoration(4, contestList.lastIndex))
         setHasFixedSize(true)
      }
   }

   private fun setExtraCurricular() {
      // 대외활동
      extracurricularRecyclerViewAdapter = ExtracurricularInterestItemRecyclerViewAdapter(
         list = extracurricularList
      ) { goToExtraCurricular(type = it) }

      binding.extracurricularList.apply {
         adapter = extracurricularRecyclerViewAdapter
         layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         addItemDecoration(
            MarginItemDecoration(4, extracurricularList.lastIndex))
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
               setContest()
            }

            is NetworkResult.Loading -> {
               Log.w("getMyInfo: ", "loading")
            }

            else -> {
               Log.w("getMyInfo error: ", response.message.toString())
               UserPreference.init()
               goToLogin()
            }
         }
      }
   }

   private fun showUserProfile(userId: Int) {
      viewModel.getUsers("Bearer ${UserPreference.accessToken}", userId).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("showUserProfile: ", response.data?.message.toString())
               val userProfileDialog = response.data?.user?.let { UserProfileDialog(requireContext(), it) }
               userProfileDialog?.onClickListener {
                  findNavController().navigate(
                     R.id.action_mainFragment_to_webViewFragment,
                     bundleOf("url" to it))
               }
               userProfileDialog?.show(childFragmentManager, null)
            }

            is NetworkResult.Error -> {
               Log.w("showUserProfile: ", "error ${response.message}")
            }

            else -> {
               Log.i("showUserProfile: ", "loading")
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
      UserPreference.interests = user.interests.joinToString(separator = ",") ?: ""
      UserPreference.name = user.name
      UserPreference.phoneNumber = user.phone
      UserPreference.gender = user.gender
      UserPreference.birthday = user.birthDate
      UserPreference.job = user.job
      UserPreference.introduce = user.introduce
      UserPreference.capability = user.capability
      UserPreference.mbti = user.userPersonality ?: ""
      UserPreference.percentage = user.percentage
      UserPreference.joinChannelCount = user.joinChannelCount
      UserPreference.waitingChannelCount = user.waitingChannelCount
   }
}