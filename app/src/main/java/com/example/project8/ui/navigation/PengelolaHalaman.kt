package com.example.project8.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project8.ui.view.DestinasiDetail
import com.example.project8.ui.view.DestinasiEntry
import com.example.project8.ui.view.DestinasiHome
import com.example.project8.ui.view.DestinasiUpdate
import com.example.project8.ui.view.DetailMhsScreen
import com.example.project8.ui.view.EntryMhsScreen
import com.example.project8.ui.view.HomeScreen
import com.example.project8.ui.view.UpdateMhsScreen

@Composable
fun PengelolaHalaman (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")

                }

            )
        }
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailMhsScreen(
                    navigateBack = { navController.navigateUp() },
                    onEditClick = { nim ->
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                        println(nim)
                    }
                )
            }

        }

        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMhsScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigate(DestinasiHome.route)
                    {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}