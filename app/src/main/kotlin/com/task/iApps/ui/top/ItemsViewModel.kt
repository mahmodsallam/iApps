package com.task.iApps.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.iApps.data.repository.ItemsRepository
import com.task.iApps.model.Resource
import com.task.iApps.model.ResponseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ItemsViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    private val submitEvent = MutableSharedFlow<Unit>()
    private val resource = submitEvent
        .flatMapLatest { repository.getItemsList() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Resource.Loading)

    val isLoading = resource.map {
        it.isLoading
    }
    val isFail = resource.map {
        it.isFail
    }
    val data = resource.map {
        it.valueOrNull?.items?.sortedBy { item: ResponseModel.Item? -> item?.published }.orEmpty()
    }

    init {
        viewModelScope.launch {
            submitEvent.emit(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            submitEvent.emit(Unit)
        }
    }
}