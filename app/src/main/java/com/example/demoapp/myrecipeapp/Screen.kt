package com.example.demoapp.myrecipeapp

sealed class Screen(val route : String) {
    object RecipeScreen:Screen("recipescreen")
    object DetailScreen:Screen("detailscreen")
}