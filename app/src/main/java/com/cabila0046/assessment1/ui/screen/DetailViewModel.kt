package com.cabila0046.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabila0046.assessment1.database.PinjamanDao
import com.cabila0046.assessment1.model.Pinjaman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: PinjamanDao): ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(nama: String, total: String, bunga:String, bulan: String) {
        val pinjaman = Pinjaman(
            tanggal = formatter.format(Date()),
            nama = nama,
            total = total,
            bunga = bunga,
            bulan = bulan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(pinjaman)
        }

    }
    suspend fun getPinjaman(id: Long): Pinjaman? {
        return dao.getPinjamanById(id)
    }

    fun update(id: Long, nama: String, total: String, bunga: String, bulan: String) {
        val pinjaman = Pinjaman(
            id = id,
            tanggal = formatter.format(Date()),
            nama = nama,
            total = total,
            bunga = bunga,
            bulan = bulan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(pinjaman)
        }
    }
    fun softDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDeleteById(id)
        }
    }



}