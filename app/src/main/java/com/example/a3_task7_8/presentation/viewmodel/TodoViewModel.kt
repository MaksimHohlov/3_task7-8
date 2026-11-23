package com.example.a3_task7_8.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a3_task7_8.domain.model.TodoItem
import com.example.a3_task7_8.domain.usecase.GetTodosUseCase
import com.example.a3_task7_8.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.launch

class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : ViewModel() {

    val todos = mutableStateOf<List<TodoItem>>(emptyList())

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            todos.value = getTodosUseCase()
        }
    }

    fun toggleTodo(id: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
            loadTodos() // обновляем список
        }
    }
}