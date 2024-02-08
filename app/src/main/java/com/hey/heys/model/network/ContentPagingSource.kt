package com.hey.heys.model.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hey.heys.api.ContentApi
import javax.inject.Inject

class ContentPagingSource @Inject constructor(
   private val contentApi: ContentApi,
   private val token: String,
   private val type: String?,
   private val order: String?,
   private val interest: ArrayList<String>?,
   private val lastRecruitDate: String?,
   private val includeClosed: Boolean?
) : PagingSource<Int, Content>() {
   override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey
      }
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
      val pageNum = params.key ?: 1

      return try {
         val response = contentApi.getContentList(
            token = token,
            type = type,
            order = order,
            interests = interest,
            lastRecruitDate = lastRecruitDate,
            includeClosed = includeClosed,
            page = pageNum,
            limit = 30)

         val data = response.body()?.data
         LoadResult.Page(
            data = data!!,
            prevKey = if (pageNum == 1) null else pageNum - 1,
            nextKey = if (data.isEmpty()) null else pageNum + 1)
      } catch (e: Exception) {
         LoadResult.Error(e)
      }
   }

}