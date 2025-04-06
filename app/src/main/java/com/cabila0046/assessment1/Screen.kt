package com.cabila0046.assessment1

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")

}