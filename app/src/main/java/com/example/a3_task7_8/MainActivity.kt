package com.example.a3_task7_8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.a3_task7_8.data.local.TodoJsonDataSource
import com.example.a3_task7_8.data.repository.TodoRepositoryImpl
import com.example.a3_task7_8.domain.usecase.GetTodosUseCase
import com.example.a3_task7_8.domain.usecase.ToggleTodoUseCase
import com.example.a3_task7_8.navigation.AppNavigation
import com.example.a3_task7_8.presentation.viewmodel.TodoViewModel
import com.example.a3_task7_8.ui.theme._3_task78Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // сборка зависимостей
        val dataSource = TodoJsonDataSource(this)
        val repository = TodoRepositoryImpl(dataSource)
        val getTodosUseCase = GetTodosUseCase(repository)
        val toggleTodoUseCase = ToggleTodoUseCase(repository)
        val viewModel = TodoViewModel(getTodosUseCase, toggleTodoUseCase)

        setContent {
            _3_task78Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}