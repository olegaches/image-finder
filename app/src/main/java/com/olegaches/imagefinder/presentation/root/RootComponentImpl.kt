package com.olegaches.imagefinder.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.olegaches.imagefinder.presentation.image_source.ImageSourceComponentImpl
import com.olegaches.imagefinder.presentation.images_root.ImagesRootComponentImpl
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class RootComponentImpl(
    @Assisted componentContext: ComponentContext,
    private val imagesRootComponentFactory: (
        ComponentContext,
        (String) -> Unit,
    ) -> ImagesRootComponentImpl,
): ComponentContext by componentContext, RootComponent {
    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
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
    ): RootComponent.Child {
        return when(config) {
            is ChildConfig.Images -> {
                RootComponent.Child.ImagesRootComponent(imagesRootComponentFactory(
                    componentContext,
                    { navigation.push(ChildConfig.ImageSource(it)) }
                ))
            }
            is ChildConfig.ImageSource -> {
                RootComponent.Child.ImageSourceComponent(
                    ImageSourceComponentImpl(
                        componentContext,
                        config.url,
                        navigation::pop
                    )
                )
            }
        }
    }

    @Serializable
    private sealed interface ChildConfig {
        @Serializable
        data object Images : ChildConfig
        @Serializable
        data class ImageSource(val url: String) : ChildConfig
    }
}