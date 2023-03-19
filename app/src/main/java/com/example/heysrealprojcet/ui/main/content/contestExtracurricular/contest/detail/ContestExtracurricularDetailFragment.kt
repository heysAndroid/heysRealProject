package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.CustomSnackBar
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularDetailFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class ContestExtracurricularDetailFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularDetailFragmentBinding
   private val viewModel: ContestExtracurricularDetailViewModel by viewModels()

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
      binding = ContestExtracurricularDetailFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.goToHeys.setOnClickListener { goToHeys() }

//      if(binding.introduce.lineCount > 5) {
//         binding.layoutExpand.visibility = View.VISIBLE
//         binding.detail.visibility = View.GONE
//      }
//
//      binding.detail.setOnClickListener {
//         if (binding.layoutExpand.visibility == View.VISIBLE) {
//            binding.layoutExpand.visibility = View.GONE
//         } else {
//            binding.layoutExpand.visibility = View.VISIBLE
//         }
//      }

      binding.btnShare.setOnClickListener {
         val bottomSheet = ContestShareBottomSheet()
         bottomSheet.show(childFragmentManager, null)
      }

      binding.bookmarkButton.setOnClickListener {
         it.isSelected = it.isSelected != true

         if (it.isSelected) {
            CustomSnackBar(binding.root, "내 관심에 추가했어요!", null, true).show()
         }
      }

      binding.lookButton.setOnClickListener { goToLook() }
   }

   private fun goToLook() {
      findNavController().navigate(R.id.action_contestExtracurricularDetailFragment_to_contestDetailLookFragment)
   }

   private fun goToHeys() {
      findNavController().navigate(R.id.action_contestExtracurricularDetailFragment_to_channelListFragment)
   }
}