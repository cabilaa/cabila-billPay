package com.cabila0046.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabila0046.assessment1.database.PinjamanDao
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: PinjamanDao) : ViewModel() {

    val data: StateFlow<List<Pinjaman>> = dao.getPinjaman().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
    fun undoDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.undoDeleteById(id)
        }
    }
    fun deletePermanent(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)

        }
    }

    val deletedData: StateFlow<List<Pinjaman>> = dao.getPinjamanDihapus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


}