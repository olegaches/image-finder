package com.olegaches.imagefinder

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import com.olegaches.imagefinder.di.AppComponent
import com.olegaches.imagefinder.di.create
import com.olegaches.imagefinder.presentation.RootComponent
import com.olegaches.imagefinder.presentation.RootScreen
import com.olegaches.imagefinder.ui.theme.ImageFinderTheme
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject

@Inject
class RootComponentCreator(
    private val rootComponentFactory: (ComponentContext) -> RootComponent,
) {
    fun create(componentContext: ComponentContext): RootComponent {
        return rootComponentFactory(componentContext)
    }
}

@Component
abstract class ActivityComponent(@Component val appComponent: AppComponent) {
    abstract val rootComponentCreator: RootComponentCreator
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent { componentContext ->
            ActivityComponent::class.create(
                AppComponent::class.create(applicationContext)
            ).rootComponentCreator.create(componentContext)
        }
        setContent {
            ChangeSystemBarsTheme(!isSystemInDarkTheme())
            ImageFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootScreen(rootComponent = root)
                }
            }
        }
    }

    @Composable
    private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
        val barColor = Color.TRANSPARENT
        DisposableEffect(lightTheme) {
            if (lightTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                )
            } else {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                )
            }
            onDispose {}
        }
    }
}