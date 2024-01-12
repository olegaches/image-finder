package com.olegaches.imagefinder.presentation.image_detail

import com.olegaches.imagefinder.presentation.image_detail_topbar.DetailTopBarComponent
import com.olegaches.imagefinder.presentation.image_detail_bottombar.ImageDetailBottomBarComponent
import com.olegaches.imagefinder.presentation.pager.PagerComponent

interface ImageDetailComponent {
    val detailTopBarComponent: DetailTopBarComponent
    val pagerComponent: PagerComponent
    val detailBottomBarComponent: ImageDetailBottomBarComponent
}
