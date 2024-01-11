import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop

private val emptyContent: @Composable ColumnScope.() -> Unit = {}

class DeclarativeModalBottomSheetState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetContent: State<@Composable ColumnScope.() -> Unit>,
    val sheetState: SheetState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <C : Any, T : Any> rememberDeclarativeModalBottomSheetState(
    slot: Value<ChildSlot<C, T>>,
    onDismiss: () -> Unit,
    skipPartiallyExpanded: Boolean = true,
    sheetContent: @Composable (child: Child.Created<C, T>) -> Unit,
): DeclarativeModalBottomSheetState {
    val slotState by slot.subscribeAsState()
    val child = slotState.child
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
    )
    val childContent = remember { mutableStateOf(emptyContent) }

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.isVisible }
            .distinctUntilChanged()
            .drop(1)
            .collect { visible ->
                if (visible.not()) {
                    onDismiss()
                }
            }
    }

    LaunchedEffect(child == null) {
        if (child == null) {
            sheetState.hide()
            childContent.value = emptyContent
        } else {
            sheetState.expand()
        }
    }

    DisposableEffect(child) {
        if (child != null) {
            childContent.value = { sheetContent(child) }
        }
        onDispose {}
    }

    return remember {
        DeclarativeModalBottomSheetState(
            sheetContent = childContent,
            sheetState = sheetState
        )
    }
}