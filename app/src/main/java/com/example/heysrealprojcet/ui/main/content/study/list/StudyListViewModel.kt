package com.example.heysrealprojcet.ui.main.content.study.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelRegion
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.StudyList
import com.example.heysrealprojcet.model.network.response.StudyListResponse
import com.example.heysrealprojcet.repository.StudyRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudyListViewModel @Inject constructor(
   private val studyRepository: StudyRepository
) : BaseViewModel() {
   private val _studyList = MutableLiveData<List<StudyList>>()
   val studyList: LiveData<List<StudyList>> = _studyList

   private val _response: MutableLiveData<NetworkResult<StudyListResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<StudyListResponse>> = _response

   fun setStudyList(list: List<StudyList>?) {
      _studyList.value = list ?: listOf()
   }

   fun getStudyList(
      token: String,
      interest: ArrayList<String>,
      lastRecruitDate: String,
      purposes: ArrayList<String>,
      online: String? = ChannelForm.Both.engForm,
      location: String? = ChannelRegion.Seoul.region,
      includeClosed: Boolean? = true,
      page: Int? = 1,
      limit: Int? = 1) = studyRepository.getStudyList(token, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit).asLiveData()

   /*= viewModelScope.launch {
      studyRepository.getStudyList(token, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit).collect { values ->
         _response.value = values
      }
   }

    */
}