package com.hey.heys.ui.main.content.study.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hey.heys.model.network.ChannelList
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.ChannelListResponse
import com.hey.heys.repository.StudyRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StudyListViewModel @Inject constructor(
   private val studyRepository: StudyRepository
) : BaseViewModel() {
   val isChecked = MutableStateFlow(false)

   private val _response: MutableLiveData<NetworkResult<ChannelListResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<ChannelListResponse>> = _response

   fun getStudyList(
      token: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean? = true): Flow<PagingData<ChannelList>> =
      studyRepository.getStudyList(token, interest, lastRecruitDate, purposes, online, location, includeClosed).cachedIn(viewModelScope)
}