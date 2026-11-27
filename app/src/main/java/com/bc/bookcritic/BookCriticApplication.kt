package com.bc.bookcritic

import android.app.Application
import android.util.Log
import com.bc.model.repository.IBookRepository
import com.bc.model.repository.di.ApplicationScope
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class BookCriticApplication : Application() {

    @Inject
    lateinit var bookRepository: IBookRepository

    @Inject
    @ApplicationScope
    lateinit var appScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        appScope.launch {
            runCatching { bookRepository.seedPredefinedGenres() }
                .onFailure { Log.e("BookCriticApp", "Genre seeding failed", it) }
        }
    }
}