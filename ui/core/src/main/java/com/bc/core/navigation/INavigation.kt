package com.bc.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

typealias EntryProviderInstaller = EntryProviderScope<NavKey>.() -> Unit


interface INavigation {
    val mainBackStack: List<NavKey>

    fun goTo(destination: NavKey)

    fun goBack()
}