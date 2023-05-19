package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.Content
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ContentListResponse
import com.example.heysrealprojcet.repository.ContentRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContestListViewModel @Inject constructor(
   private val contentRepository: ContentRepository
) : BaseViewModel() {
   private val _contestList = MutableLiveData<List<Content>>()
   val contestList: LiveData<List<Content>> = _contestList

   private val _response: MutableLiveData<NetworkResult<ContentListResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<ContentListResponse>> = _response

   fun setContestList(list: List<Content>?) {
      _contestList.value = list ?: listOf()
   }

    fun getContestList(
      token: String,
      type: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean? = true,
      page: Int? = 1,
      limit: Int? = 20) = contentRepository.getContentList(token, type, interest, lastRecruitDate, includeClosed, page, limit).asLiveData()
}