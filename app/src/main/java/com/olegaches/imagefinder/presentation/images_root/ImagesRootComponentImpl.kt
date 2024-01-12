package com.olegaches.imagefinder.presentation.images_root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.presentation.image_filter.FilterComponentImpl
import com.olegaches.imagefinder.presentation.image_detail.ImageDetailComponentImpl
import com.olegaches.imagefinder.presentation.util.ImagePositionalParam
import com.olegaches.imagefinder.presentation.images.ImagesComponentImpl
import com.olegaches.imagefinder.presentation.image_list.ImagesListEvent
import com.olegaches.imagefinder.presentation.pager.PagerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesRootComponentImpl(
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    private val navigateToImageSource: (String) -> Unit,
    imagesComponentFactory: (
        ComponentContext,
        (Int, Image) -> Unit,
        (ImagePositionalParam) -> Unit,
        () -> Unit,
        (SearchFilter?) -> Unit
    ) -> ImagesComponentImpl
): ComponentContext by componentContext, ImagesRootComponent {
    private val pagerNavigation = SlotNavigation<PagerConfig>()
    private val sheetNavigation = SlotNavigation<SheetConfig>()
    private val filterState = MutableStateFlow<SearchFilter?>(null)

    override val imagesComponent = imagesComponentFactory(
        childContext(key = "imagesComponent"),
        { index, image ->
            pagerNavigation.activate(PagerConfig.Pager(index = index, image = image))
        },
        { imagePosParam ->
            val pagerComponent = (pagerSlot.value.child?.instance as?
                    ImagesRootComponent.PagerChild.ImageDetail)?.component?.pagerComponent
            pagerComponent?.handleEvent(PagerEvent.OnAnimateImage(imagePosParam))
        },
        { sheetNavigation.activate(SheetConfig.Filter) },
        { filter -> filterState.update { filter } }
    )

    override fun dismissSheet() {
        sheetNavigation.dismiss()
    }

    override val pagerSlot: Value<ChildSlot<*, ImagesRootComponent.PagerChild>> =
        childSlot(
            source = pagerNavigation,
            handleBackButton = true,
            key = "PagerSlot",
            childFactory = ::createPagerChild,
            serializer = PagerConfig.serializer()
        )

    override val sheetSlot: Value<ChildSlot<*, ImagesRootComponent.SheetChild>> =
        childSlot(
            source = sheetNavigation,
            handleBackButton = true,
            key = "SheetSlot",
            childFactory = ::createSheetChild,
            serializer = SheetConfig.serializer()
        )

    private fun createPagerChild(
        config: PagerConfig,
        componentContext: ComponentContext
    ): ImagesRootComponent.PagerChild {
        return when(config) {
            is PagerConfig.Pager -> {
                val imageListComponent = imagesComponent.imagesListComponent
                ImagesRootComponent.PagerChild.ImageDetail(
                    ImageDetailComponentImpl(
                        componentContext,
                        pagerNavigation::dismiss,
                        config.index,
                        config.image,
                        imageListComponent.listState,
                        { index ->
                            imageListComponent.handleEvent(
                                ImagesListEvent.OnScrollToImage(
                                    index
                                )
                            )
                        },
                        navigateToImageSource
                    )
                )
            }
        }
    }

    private fun createSheetChild(
        config: SheetConfig,
        componentContext: ComponentContext
    ): ImagesRootComponent.SheetChild {
        return when(config) {
            is SheetConfig.Filter -> {
                ImagesRootComponent.SheetChild.Filter(
                    FilterComponentImpl(
                        componentContext,
                        { searchFilter ->
                            sheetNavigation.dismiss {
                                val imagesComponent = imagesComponent
                                val query = imagesComponent.topBarComponent.state.value.query
                                imagesComponent.imagesListComponent.handleEvent(
                                    ImagesListEvent.SearchImages(
                                        query,
                                        searchFilter
                                    )
                                )
                            }
                        },
                        filterState.value
                    )
                )
            }
        }
    }

    @Serializable
    private sealed interface PagerConfig {
        @Serializable
        data class Pager(val index: Int, val image: Image) : PagerConfig
    }

    @Serializable
    private sealed interface SheetConfig {
        @Serializable
        data object Filter : SheetConfig
    }
}