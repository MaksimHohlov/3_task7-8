package com.example.a3_task7_8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.a3_task7_8.domain.model.TodoItem
import com.example.a3_task7_8.presentation.ui.screen.TodoDetailScreen
import com.example.a3_task7_8.presentation.ui.screen.TodoListScreen
import com.example.a3_task7_8.presentation.viewmodel.TodoViewModel
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class DetailRoute(val id: Int)

@Composable
fun AppNavigation(viewModel: TodoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListRoute) {
        composable<ListRoute> {
            TodoListScreen(
                todos = viewModel.todos.value,
                onItemClick = { item ->
                    navController.navigate(DetailRoute(item.id))
                },
                onToggle = { id ->
                    viewModel.toggleTodo(id)
                }
            )
        }
        composable<DetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<DetailRoute>()
            val selectedItem = viewModel.todos.value.find { it.id == route.id }
            if (selectedItem != null) {
                TodoDetailScreen(
                    item = selectedItem,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}