package com.example.a3_task7_8.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a3_task7_8.domain.model.TodoItem
import com.example.a3_task7_8.presentation.ui.component.TodoItemCard

@Composable
fun TodoListScreen(
    todos: List<TodoItem>,
    onItemClick: (TodoItem) -> Unit,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todos, key = { it.id }) { item ->
            TodoItemCard(
                item = item,
                onCheckboxChange = { onToggle(item.id) },
                onClick = { onItemClick(item) }
            )
        }
    }
}