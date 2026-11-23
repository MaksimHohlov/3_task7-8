package com.example.a3_task7_8.data.repository

import com.example.a3_task7_8.data.local.TodoJsonDataSource
import com.example.a3_task7_8.domain.model.TodoItem
import com.example.a3_task7_8.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val dataSource: TodoJsonDataSource
) : TodoRepository {

    // список в памяти
    private var cachedTodos: List<TodoItem> = emptyList()

    override suspend fun getTodos(): List<TodoItem> {
        if (cachedTodos.isEmpty()) {
            cachedTodos = dataSource.getTodos().map {
                TodoItem(it.id, it.title, it.description, it.isCompleted)
            }
        }
        return cachedTodos
    }

    override suspend fun toggleTodo(id: Int) {
        cachedTodos = cachedTodos.map {
            if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
        }
    }
}