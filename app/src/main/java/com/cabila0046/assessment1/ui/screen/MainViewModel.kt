package com.cabila0046.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabila0046.assessment1.database.PinjamanDao
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: PinjamanDao) : ViewModel() {

    val data: StateFlow<List<Pinjaman>> = dao.getPinjaman().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}