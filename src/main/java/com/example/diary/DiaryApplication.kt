package com.example.diary

import android.app.Application
import com.example.diary.data.AppContainer
import com.example.diary.data.AppDataContainer

class DiaryApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}