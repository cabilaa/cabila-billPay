package com.cabila0046.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import com.cabila0046.assessment1.model.Pinjaman

class MainViewModel : ViewModel() {

    val data = listOf(
        Pinjaman(
            1,
            "cabila",
            "10.000.000",
            "2%",
            "12 bulan",
            "2025-05-03 12:24:43"
        ),
        Pinjaman(
            2,
            "vani",
            "9.000.000",
            "8%",
            "6 bulan",
            "2025-06-03 10:34:3"
        ),
    )
}