package com.example.a3_task7_8.domain.usecase

import com.example.a3_task7_8.domain.repository.TodoRepository

class ToggleTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) = repository.toggleTodo(id)
}