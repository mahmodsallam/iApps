package com.task.iApps.data.repository

import com.task.iApps.model.Resource
import com.task.iApps.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItemsList(): Flow<Resource<ResponseModel>>
}