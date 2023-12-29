package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class RootComponent(
    @Assisted componentContext: ComponentContext,
    private val imagesRootComponentFactory: (ComponentContext) -> ImagesRootComponent,
): ComponentContext by componentContext, IRootComponent {
    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: Value<ChildStack<*, IRootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = ChildConfig.serializer(),
            initialConfiguration = ChildConfig.Images,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): IRootComponent.Child {
        return when(config) {
            is ChildConfig.Images -> {
                IRootComponent.Child.ImagesRootComponent(imagesRootComponentFactory(componentContext))
            }
            is ChildConfig.ImageSource -> {
                TODO()
            }
        }
    }

    @Serializable
    private sealed interface ChildConfig {
        @Serializable
        data object Images : ChildConfig
        @Serializable
        data object ImageSource : ChildConfig
    }
}