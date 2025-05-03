package com.cabila0046.assessment1.navigation

import com.cabila0046.assessment1.ui.screen.KEY_ID_PINJAMAN

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Bunga: Screen("bungaScreen")
    data object FormBaru: Screen("bungaDetailScreen")
    data object FormUbah: Screen("bungaDetailScreen/{$KEY_ID_PINJAMAN}") {
        fun withId(id: Long) = "bungaDetailScreen/$id"
    }
    data object RecycleBin: Screen("recycleBinScreen")

}

