package com.example.farebaseauthentication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farebaseauthentication.presentation.login.LoginScreen
import com.example.farebaseauthentication.presentation.signup.SignUpScreen

@Composable
fun NavigationGraph(navController: NavHostController= rememberNavController()){
    NavHost(navController = navController, startDestination = "login"){
        composable( "login"){
            LoginScreen(navController = navController)
        }
        composable("register"){
            SignUpScreen(Modifier)
        }
    }
}