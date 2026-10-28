package com.bc.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home: NavKey

@Serializable
data object Library : NavKey

@Serializable
data object Stats : NavKey

@Serializable
data object AddLibraryBook: NavKey

@Serializable
data class BookDetails(val id: String) : NavKey

