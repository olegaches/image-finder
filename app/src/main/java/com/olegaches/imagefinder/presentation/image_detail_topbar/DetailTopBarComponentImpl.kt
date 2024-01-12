package com.olegaches.imagefinder.presentation.image_detail_topbar

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailTopBarComponentImpl(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
): ComponentContext by componentContext, DetailTopBarComponent {
    private val _state = MutableStateFlow(DetailTopBarState())
    override val state = _state.asStateFlow()

    init {
        changeVisibility(true)
    }

    override fun changeVisibility(visible: Boolean) {
        _state.update { it.copy(visible = visible) }
    }

    override fun onBackIconClicked() {
        changeVisibility(false)
        onBackClicked()
    }
}
