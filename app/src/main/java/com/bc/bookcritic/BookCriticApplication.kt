package com.bc.bookcritic

import android.app.Application
import android.util.Log
import com.bc.model.repository.IBookRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class BookCriticApplication : Application() {

    @Inject
    lateinit var bookRepository: IBookRepository

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            runCatching {
                bookRepository.seedPredefinedGenres()
            }.onFailure {
                Log.e("BookCriticApp", "Genre seeding failed", it)
            }
        }
    }
}