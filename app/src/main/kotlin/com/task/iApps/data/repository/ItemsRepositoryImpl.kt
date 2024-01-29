package com.task.iApps.data.repository

import com.task.iApps.data.api.ItemsApi
import com.task.iApps.model.Resource
import com.task.iApps.model.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemsRepositoryImpl(
    private val api: ItemsApi
) : ItemsRepository {
    override fun getItemsList(
    ): Flow<Resource<ResponseModel>> = flow {
        emit(Resource.Loading)
        val resource = try {
            val response = api.getItemsList()
            Resource.Success(response)
        } catch (e: Throwable) {
            Resource.Fail(e)
        }
        emit(resource)
    }
}