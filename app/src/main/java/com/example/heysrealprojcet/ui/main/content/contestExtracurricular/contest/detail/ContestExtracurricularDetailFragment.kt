package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.heysrealprojcet.CustomSnackBar
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularDetailFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContestExtracurricularDetailFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularDetailFragmentBinding
   private val viewModel: ContestExtracurricularDetailViewModel by viewModels()
   val args: ContestExtracurricularDetailFragmentArgs by navArgs()
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

      contentViewCountUp(args.contentID)
      getContentDetail(args.contentID)

      with(binding) {
         goToHeys.setOnClickListener { goToHeys() }
         btnShare.setOnClickListener {
            val bottomSheet = ContestShareBottomSheet()
            bottomSheet.show(childFragmentManager, null)
         }

         bookmarkButton.setOnClickListener {
            it.isSelected = it.isSelected != true
            if (it.isSelected) {
               contentAddBookmark(args.contentID)
            } else {
               contentRemoveBookmark(args.contentID)
            }
         }
         zoomButton.setOnClickListener { viewModel.thumbnailUri.value?.let { goToZoom(it) } }
      }
   }

   private fun goToZoom(imageUri: String) {
      findNavController().navigate(
         R.id.action_contestExtracurricularDetailFragment_to_contestDetailLookFragment,
         bundleOf("imageUri" to imageUri))
   }

   private fun goToHeys() {
      findNavController().navigate(R.id.action_contestExtracurricularDetailFragment_to_channelListFragment)
   }

   private fun getContentDetail(id: Int) {
      viewModel.getContentDetail("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.receiveContentDetail(response.data?.contentDetail)
               Glide.with(requireContext()).load(viewModel.thumbnailUri.value).into(binding.thumbnail)
               viewModel.dday.value?.let { setDday(it) }
               setBookmark()
            }

            is NetworkResult.Error -> {
               Log.w("getContentDetail: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getContentDetail: ", "loading")
            }
         }
      }
   }

   private fun contentViewCountUp(id: Int) {
      viewModel.contentViewCountUp("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentViewCountUp: ", response.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("contentViewCountUp: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("contentViewCountUp: ", "loading")
            }
         }
      }
   }

   private fun contentAddBookmark(id: Int) {
      viewModel.contentAddBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentAddBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에 추가했어요!", null, true, subMessage = "보러가기").show()
            }

            is NetworkResult.Error -> {
               Log.w("contentAddBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에 추가하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("contentAddBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에 추가하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun contentRemoveBookmark(id: Int) {
      viewModel.contentRemoveBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentRemoveBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에서 제거했어요!", null, true).show()
            }

            is NetworkResult.Error -> {
               Log.w("contentRemoveBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에서 제거하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("contentRemoveBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에서 제거하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun setDday(dday: Int) {
      when {
         dday < 0 -> {
            binding.dday.text = "마감"
            binding.dday.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
         }

         dday in 1..6 -> {
            binding.dday.text = "D-${dday}"
            binding.dday.setBackgroundResource(R.drawable.bg_fd494a_radius_4)
         }

         else -> {
            binding.dday.text = "D-${dday}"
            binding.dday.setBackgroundResource(R.drawable.bg_34d676_radius_4)
         }
      }
   }

   private fun setBookmark() {
      viewModel.isBookMarked.observe(viewLifecycleOwner) {
         binding.bookmarkButton.isSelected = it
      }
   }
}