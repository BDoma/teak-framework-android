package teak.framework.android.experimental

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
fun <Model : Any, Msg> ComponentActivity.useTeakViewModel(
    teakVM: TeakViewModel<Model, Msg>,
    view: (Model, (Msg) -> Unit) -> Unit
) {
    lifecycleScope.launch {
        teakVM.state.collect {
            it.first?.let { model ->
                view(model, it.second)
            }
        }
    }
}

@MainThread
fun <Model: Any, Msg> ComponentActivity.teakViewModel(
    init: () -> Pair<Model, List<() -> Msg>>,
    update: (model: Model, message: Msg) -> Pair<Model, List<() -> Msg>>,
): Lazy<TeakViewModel<Model, Msg>> {
    return viewModels<TeakViewModel<Model, Msg>> {
        TeakViewModel.createFactory(
            init,
            update
        )
    }
}

fun <Model : Any, Msg> Fragment.useTeakViewModel(
    teakVM: TeakViewModel<Model, Msg>,
    view: (Model, (Msg) -> Unit) -> Unit
) {
    lifecycleScope.launch {
        teakVM.state.collect {
            it.first?.let { model ->
                view(model, it.second)
            }
        }
    }
}

@MainThread
fun <Model: Any, Msg> Fragment.teakViewModel(
    init: () -> Pair<Model, List<() -> Msg>>,
    update: (model: Model, message: Msg) -> Pair<Model, List<() -> Msg>>,
): Lazy<TeakViewModel<Model, Msg>> {
    return viewModels<TeakViewModel<Model, Msg>> {
        TeakViewModel.createFactory(
            init,
            update
        )
    }
}