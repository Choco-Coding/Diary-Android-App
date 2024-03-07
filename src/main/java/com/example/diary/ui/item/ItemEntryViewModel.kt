package com.example.diary.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diary.data.Item
import com.example.diary.data.ItemsRepository

class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            year.isNotBlank() && month.isNotBlank() && day.isNotBlank() && title.isNotBlank() && contents.isNotBlank()
        }
    }
}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val title: String = "",
    val contents: String = ""
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    year = year.toIntOrNull() ?: 0,
    month = month.toIntOrNull() ?: 0,
    day = day.toIntOrNull() ?: 0,
    title = title,
    contents = contents
)

fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    year = year.toString(),
    month = month.toString(),
    day = day.toString(),
    title = title,
    contents = contents
)