package com.example.a3_task7_8

import com.example.a3_task7_8.domain.model.TodoItem
import com.example.a3_task7_8.domain.repository.TodoRepository
import com.example.a3_task7_8.domain.usecase.GetTodosUseCase
import com.example.a3_task7_8.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeTodoRepository : TodoRepository {
    private var todos = listOf(
        TodoItem(1, "Task 1", "Desc", false),
        TodoItem(2, "Task 2", "Desc", true),
        TodoItem(3, "Task 3", "Desc", false)
    )

    override suspend fun getTodos(): List<TodoItem> = todos

    override suspend fun toggleTodo(id: Int) {
        todos = todos.map { if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it }
    }
}

class UseCaseTest {

    // 1. GetTodosUseCase возвращает 3 задачи
    @Test
    fun getTodosUseCase_returnsThreeTasks() = runTest {
        val fakeRepo = FakeTodoRepository()
        val useCase = GetTodosUseCase(fakeRepo)

        val result = useCase()

        assertEquals(3, result.size)
    }

    // 2. toggleTodo меняет isCompleted
    @Test
    fun toggleTodoUseCase_changesStatus() = runTest {
        val fakeRepo = FakeTodoRepository()
        val useCase = ToggleTodoUseCase(fakeRepo)

        useCase(1)

        val updatedTask = fakeRepo.getTodos().find { it.id == 1 }
        assertTrue(updatedTask?.isCompleted == true)
    }
}