package com.example.a3_task7_8.domain.repository

import com.example.a3_task7_8.domain.model.TodoItem

interface TodoRepository {
    suspend fun getTodos(): List<TodoItem>
    suspend fun toggleTodo(id: Int)
}