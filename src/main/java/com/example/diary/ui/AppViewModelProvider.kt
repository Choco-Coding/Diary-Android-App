package com.example.diary.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.diary.DiaryApplication
import com.example.diary.ui.home.HomeViewModel
import com.example.diary.ui.item.ItemDetailsViewModel
import com.example.diary.ui.item.ItemEditViewModel
import com.example.diary.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                diaryApplication().container.itemsRepository
            )
        }
        // Initializer for ItemRegistrationViewModel
        initializer {
            ItemEntryViewModel(diaryApplication().container.itemsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                diaryApplication().container.itemsRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(diaryApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.diaryApplication(): DiaryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DiaryApplication)