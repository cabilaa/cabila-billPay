package com.cabila0046.assessment1

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Bunga: Screen("bungaScreen")
    data object FormBaru: Screen("bungaDetailScreen")

}