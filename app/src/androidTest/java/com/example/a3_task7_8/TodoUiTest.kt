package com.example.a3_task7_8

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.a3_task7_8.data.local.TodoJsonDataSource
import com.example.a3_task7_8.data.repository.TodoRepositoryImpl
import com.example.a3_task7_8.domain.usecase.GetTodosUseCase
import com.example.a3_task7_8.domain.usecase.ToggleTodoUseCase
import com.example.a3_task7_8.navigation.AppNavigation
import com.example.a3_task7_8.presentation.ui.screen.TodoListScreen
import com.example.a3_task7_8.presentation.viewmodel.TodoViewModel
import org.junit.Rule
import org.junit.Test

class TodoUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // функция для создания ViewModel с реальным JSON
    private fun setupViewModel(): TodoViewModel {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dataSource = TodoJsonDataSource(context)
        val repo = TodoRepositoryImpl(dataSource)
        return TodoViewModel(GetTodosUseCase(repo), ToggleTodoUseCase(repo))
    }

    // 3. отображаются все 3 задачи из JSON
    @Test
    fun listScreen_displaysTasksFromJson() {
        val viewModel = setupViewModel()

        composeTestRule.setContent {
            TodoListScreen(
                todos = viewModel.todos.value,
                onItemClick = {},
                onToggle = {}
            )
        }

        // Ждем, так как это suspend функция
        composeTestRule.waitUntil(5000) {
            viewModel.todos.value.isNotEmpty()
        }

        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
        composeTestRule.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()
    }

    // 4. чекбокс переключает статус
    @Test
    fun listScreen_checkboxTogglesStatus() {
        val viewModel = setupViewModel()

        composeTestRule.setContent {
            TodoListScreen(
                todos = viewModel.todos.value,
                onItemClick = {},
                onToggle = { viewModel.toggleTodo(it) }
            )
        }

        composeTestRule.waitUntil(5000) { viewModel.todos.value.isNotEmpty() }
        composeTestRule.onNodeWithTag("checkbox_1").performClick()
        composeTestRule.waitUntil(2000) {
            viewModel.todos.value.find { it.id == 1 }?.isCompleted == true
        }
    }

    // 5. навигация List -> Detail -> List
    @Test
    fun navigation_listToDetailAndBack() {
        val viewModel = setupViewModel()

        composeTestRule.setContent {
            AppNavigation(viewModel = viewModel)
        }

        composeTestRule.waitUntil(5000) { viewModel.todos.value.isNotEmpty() }
        composeTestRule.onNodeWithText("Купить молоко").performClick()
        composeTestRule.onNodeWithText("2 литра, обезжиренное").assertIsDisplayed()
        composeTestRule.onNodeWithText("Назад").performClick()
        composeTestRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
    }
}