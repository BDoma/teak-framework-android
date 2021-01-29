package com.tea.kotlin.android.component

import com.tea.kotlin.Runtime
import com.tea.kotlin.helper.Initializer
import com.tea.kotlin.helper.Updater


class TeaComponent<Model : Any, Msg>(private val implementation: Impl<Model, Msg>) {

    private lateinit var runtime: Runtime<Model, Msg>
    private lateinit var updater: Updater<Model, Msg>
    private lateinit var initer: Initializer<Model, Msg>
    private val msgSender = MsgSender<Msg>()

    fun dispatch(message: Msg){
        msgSender.dispatch(message)
    }

    internal fun onCreate() {
        updater = implementation.updater()
        initer = implementation.initializer()
        runtime = Runtime(::init, view = ::view, ::update)
    }

    internal fun onDestroy() {
        runtime.stop()
    }

    private fun init(): Pair<Model, List<() -> Msg>> {
        return initer.init()
    }

    private fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>> {
        return updater.update(model, message)
    }

    private fun view(model: Model, dispatch: (Msg) -> Unit) {
        implementation.view(model)
        msgSender.addDispatcher(dispatch)
    }

    interface Impl<Model : Any, Msg> {
        fun initializer(): Initializer<Model, Msg>
        fun view(model: Model)
        fun updater(): Updater<Model, Msg>
    }
}