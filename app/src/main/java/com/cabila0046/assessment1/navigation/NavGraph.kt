package com.cabila0046.assessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cabila0046.assessment1.Screen
import com.cabila0046.assessment1.ui.screen.MainScreen
import com.cabila0046.assessment1.ui.screen.AboutScreen
import com.cabila0046.assessment1.ui.screen.BungaDetailScreen
import com.cabila0046.assessment1.ui.screen.BungaScreen
import com.cabila0046.assessment1.ui.screen.KEY_ID_PINJAMAN

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Bunga.route) {
            BungaScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            BungaDetailScreen(navController)
        }
        composable(
            route = Screen.FormBaru.route + "/{$KEY_ID_PINJAMAN}}",
            arguments = listOf(
                navArgument(KEY_ID_PINJAMAN) { type = NavType.LongType }
            )
            ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_PINJAMAN)
            BungaDetailScreen(navController, id)
        }
    }
}