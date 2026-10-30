package com.bc.bookcritic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bc.core.navigation.EntryProviderInstaller
import com.bc.core.navigation.INavigation
import com.bc.core.theme.MaterialAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigation: INavigation

    @Inject
    lateinit var entryProviderInstallers:
            Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialAppTheme {
                BookCriticApp(
                    navigation = navigation,
                    entryProviderInstallers = entryProviderInstallers,
                )
            }
        }
    }
}
