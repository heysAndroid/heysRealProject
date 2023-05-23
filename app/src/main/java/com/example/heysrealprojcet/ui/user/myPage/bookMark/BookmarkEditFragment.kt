package com.example.heysrealprojcet.ui.user.myPage.bookMark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.BookmarkEditFragmentBinding
import com.example.heysrealprojcet.model.BookmarkData
import com.example.heysrealprojcet.ui.main.MainActivity

class BookmarkEditFragment : Fragment() {
   private lateinit var binding: BookmarkEditFragmentBinding
   private val viewModel: BookmarkEditViewModel by viewModels()
   private lateinit var bookmarkList: MutableList<BookmarkData>
   private lateinit var bookmarkEditRecyclerViewAdapter: BookmarkEditRecyclerViewAdapter

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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = BookmarkEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = viewLifecycleOwner

      makeList()

      bookmarkEditRecyclerViewAdapter = BookmarkEditRecyclerViewAdapter(viewModel, bookmarkList)
      binding.bookmarkEditList.adapter = bookmarkEditRecyclerViewAdapter
      binding.bookmarkEditList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.ivBack.setOnClickListener { findNavController().popBackStack() }

      viewModel.selectedBookmarkList.observe(viewLifecycleOwner) {
         viewModel.isEnabled.value = it.isNotEmpty()
      }

      binding.okButton.setOnClickListener {
         goToBookmarkCollection()
      }
   }

   private fun goToBookmarkCollection() {
      findNavController().navigate(
         R.id.action_bookmarkEditFragment_to_bookmarkListFragment,
         bundleOf("isSuccess" to true))
   }

   private fun makeList() {
      bookmarkList = mutableListOf(
         BookmarkData(R.drawable.bg_study_list, "수도권 팀원 \n모집해요!", "개설한지 7일", 3),
         BookmarkData(R.drawable.bg_study_list, "칠팔구십일이삼사오...", "일이삼사오육칠팔구이...", 500),
         BookmarkData(R.drawable.bg_study_list, "2022 이랜드재단 ESG 서포터즈", "이랜드재단", 1000),
         BookmarkData(R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2),
         BookmarkData(R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2),
         BookmarkData(R.drawable.bg_study_list, "제3회 \n연구개발 특구", "과학기술정보통신부, 연구개발...", 2)
      )
   }
}