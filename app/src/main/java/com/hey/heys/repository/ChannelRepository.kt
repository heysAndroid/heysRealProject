package com.hey.heys.repository

import com.hey.heys.api.ChannelApi
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Study
import com.hey.heys.model.network.response.ChannelDetailResponse
import com.hey.heys.model.network.response.ChannelFollowerListResponse
import com.hey.heys.model.network.response.ChannelListResponse
import com.hey.heys.model.network.response.CreateStudyResponse
import com.hey.heys.model.network.response.MyChannelListResponse
import com.hey.heys.model.network.response.SimpleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChannelRepository @Inject constructor(
   private val channelApi: ChannelApi
) : BaseApiResponse() {
   fun getChannelDetail(token: String, id: Int): Flow<NetworkResult<ChannelDetailResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getChannelDetail(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun getMyChannelList(token: String): Flow<NetworkResult<MyChannelListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getMyChannelList(token) })
      }.flowOn(Dispatchers.IO)
   }

   fun getContentChannelList(
      token: String,
      id: Int,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean?,
      page: Int?,
      limit: Int?
   ): Flow<NetworkResult<ChannelListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getContentChannelList(token, id, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit) })
      }.flowOn(Dispatchers.IO)
   }

   fun createContentChannel(token: String, contentId: Int, channel: Study): Flow<NetworkResult<CreateStudyResponse>> {
      return flow {
         emit(safeApiCall { channelApi.createContentChannel(token, contentId, channel) })
      }.flowOn(Dispatchers.IO)
   }

   fun channelViewCountUp(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.channelViewCountUp(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun channelAddBookmark(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.channelAddBookmark(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun channelRemoveBookmark(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.channelRemoveBookmark(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun joinChannel(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.joinChannel(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun getChannelFollower(token: String, channelId: Int, status: String): Flow<NetworkResult<ChannelFollowerListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getChannelFollower(token, channelId, status) })
      }.flowOn(Dispatchers.IO)
   }

   fun putRequestReject(token: String, channelId: Int, followerId: Int, message: SimpleResponse): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.putRequestReject(token, channelId, followerId, message) })
      }.flowOn(Dispatchers.IO)
   }

   fun putRequestAllow(token: String, channelId: Int, followerId: Int, message: SimpleResponse): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.putRequestAllow(token, channelId, followerId, message) })
      }.flowOn(Dispatchers.IO)
   }

   fun getMyChannelByStatus(token: String, status: String): Flow<NetworkResult<MyChannelListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getMyChannel(token, status) })
      }.flowOn(Dispatchers.IO)
   }

   fun putExitChannel(token: String, channelId: Int, message: SimpleResponse): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.putExitChannel(token, channelId, message) })
      }.flowOn(Dispatchers.IO)
   }

   fun putMemberAbort(token: String, channelId: Int, message: SimpleResponse): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { channelApi.putMemberAbort(token, channelId, message) })
      }.flowOn(Dispatchers.IO)
   }

   fun getAllChannelList(
      token: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean?,
      page: Int?,
      limit: Int?
   ): Flow<NetworkResult<ChannelListResponse>> {
      return flow {
         emit(safeApiCall {
            channelApi.getAllChannelList(
               token, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit)
         })
      }.flowOn(Dispatchers.IO)
   }
}