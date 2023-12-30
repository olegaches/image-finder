package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailTopBarComponent(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit
): ComponentContext by componentContext, IDetailTopBarComponent {
    private val _state = MutableStateFlow(DetailTopBarState())
    override val state = _state.asStateFlow()

    init {
        _state.update { it.copy(visible = true) }
    }

    override fun changeVisibility(visible: Boolean) {
        _state.update { it.copy(visible = visible) }
    }

    override fun onBackIconClicked() {
        changeVisibility(false)
        onBackClicked()
    }
}
