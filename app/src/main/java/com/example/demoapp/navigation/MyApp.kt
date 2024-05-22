package com.example.demoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "FirstScreen"){
        composable(route = "FirstScreen"){
            FirstScreen {name->
                navController.navigate("SecondScreen/$name")
            }
        }
        composable(route = "SecondScreen/{name}"){
            val name = it.arguments?.getString("name") ?: "No name"
            SecondScreen (name){
                navController.navigate("FirstScreen")
            }
        }
    }
}