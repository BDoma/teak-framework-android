package teak.framework.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import teak.framework.core.runtime.TeakRuntime

class TeakViewModel<Model : Any, Msg>(
    init: () -> Pair<Model, List<() -> Msg>>,
    update: (model: Model, message: Msg) -> Pair<Model, List<() -> Msg>>
) : ViewModel() {
    private val _model: MutableStateFlow<Pair<Model?, (Msg) -> Unit>> = MutableStateFlow(null to {})
    val state = _model.asStateFlow()

    init {
        TeakRuntime(init = init,
            update = update,
            view = { model, function ->
                _model.update { Pair(model, function) }
            })
    }

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