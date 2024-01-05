package com.olegaches.imagefinder.presentation

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesRootComponent(
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    private val navigateToImageSource: (String) -> Unit,
    private val imageDetailComponentFactory: (
        ComponentContext,
        () -> Unit,
        Int,
        image: Image,
        StateFlow<PagingData<Image>>,
        (Int) -> Unit,
        (String) -> Unit,
    ) -> ImageDetailComponent,
    imagesComponentFactory: (
        ComponentContext,
        (Int, Image) -> Unit,
        (ImagePositionalParam) -> Unit,
    ) -> ImagesComponent
): ComponentContext by componentContext, IImagesRootComponent {
    private val slotNavigation = SlotNavigation<SlotConfig>()

    override val imagesComponent = imagesComponentFactory(
        childContext(key = "imagesComponent"),
        { index, image ->
            slotNavigation.activate(SlotConfig.Pager(index = index, image = image))
        },
        { imagePosParam ->
            val pagerComponent = (childSlot.value.child?.instance as? IImagesRootComponent.SlotChild.ImageDetail)?.component?.pagerComponent
            pagerComponent?.handleEvent(PagerEvent.OnAnimateImage(imagePosParam))
        }
    )

    override val childSlot: Value<ChildSlot<*, IImagesRootComponent.SlotChild>> =
        childSlot(
            source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createSlotChild,
            serializer = SlotConfig.serializer()
        )

    private fun createSlotChild(
        config: SlotConfig,
        componentContext: ComponentContext
    ): IImagesRootComponent.SlotChild {
        return when(config) {
            is SlotConfig.Pager -> {
                val imageListComponent = imagesComponent.imagesListComponent
                IImagesRootComponent.SlotChild.ImageDetail(
                    imageDetailComponentFactory(
                        componentContext,
                        slotNavigation::dismiss,
                        config.index,
                        config.image,
                        imageListComponent.listState,
                        { index -> imageListComponent.handleEvent(ImagesListEvent.OnScrollToImage(index)) },
                        navigateToImageSource
                    )
                )
            }
        }
    }

    @Serializable
    private sealed interface SlotConfig {
        @Serializable
        data class Pager(val index: Int, val image: Image) : SlotConfig
    }
}