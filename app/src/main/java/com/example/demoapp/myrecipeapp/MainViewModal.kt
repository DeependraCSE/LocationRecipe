package com.example.demoapp.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModal : ViewModel() {
    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState : State<RecipeState> = _categoriesState

    init {
        fetchCategories()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.categories,
                    error = null
                )
            }catch (e : Exception){
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false, error = "Error fetching categories ${e.message}"
                )
            }
        }
    }

    data class RecipeState(val loading :Boolean = true,
            val list : List<Category> = emptyList(),
            val error : String? = null
    )
}