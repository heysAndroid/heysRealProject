package com.hey.heys.ui.main.content.contestExtracurricular.contest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hey.heys.model.network.Content
import com.hey.heys.repository.ContentRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ContestListViewModel @Inject constructor(
   private val contentRepository: ContentRepository
) : BaseViewModel() {
   private val _contestList = MutableLiveData<List<Content>>()
   val contestList: LiveData<List<Content>> = _contestList

   val isChecked = MutableStateFlow(false)

   fun setContestList(list: List<Content>?) {
      _contestList.value = list ?: listOf()
   }

   fun getContestList(
      token: String,
      type: String,
      order: String?,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean? = false): Flow<PagingData<Content>> = contentRepository.getContentList(token, type, order, interest, lastRecruitDate, includeClosed).cachedIn(viewModelScope)
}