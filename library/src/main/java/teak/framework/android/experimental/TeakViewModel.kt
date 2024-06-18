package teak.framework.android.experimental

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.annotations.ApiStatus.Experimental
import teak.framework.core.runtime.TeakRuntime

@Experimental
class TeakViewModel<Model : Any, Msg>(
    init: () -> Pair<Model, List<() -> Msg>>,
    update: (model: Model, message: Msg) -> Pair<Model, List<() -> Msg>>
) : ViewModel() {
    private var _dispatch: (Msg) -> Unit = {}
    private val _model: MutableStateFlow<Pair<Model?, (Msg) -> Unit>> = MutableStateFlow(null to _dispatch)
    val state = _model.asStateFlow()

    init {
        TeakRuntime(init = init,
            update = update,
            view = { model, function ->
                _dispatch = function
                _model.update { Pair(model, function) }
            })
    }

    fun dispatch(msg: Msg) = _dispatch(msg)

    companion object{
        fun <Model: Any, Msg>createFactory(
            init: () -> Pair<Model, List<() -> Msg>>,
            update: (model: Model, message: Msg) -> Pair<Model, List<() -> Msg>>
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TeakViewModel(init, update)
            }
        }
    }
}