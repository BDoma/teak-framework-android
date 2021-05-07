package teak.framework.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import teak.framework.core.TeakComponent
import teak.framework.core.TeakComponentContract


abstract class Activity<Model : Any, Msg : Any>(componentImpl: TeakComponentContract.Impl<Model, Msg>) : AppCompatActivity() {
    protected val teaComponent = TeakComponent(componentImpl)
    protected val viewLifecycle = ViewLifecycle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent.onCreate()
        setContentView(getLayout())
        viewLifecycle.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewLifecycle.destroy()
        teaComponent.onDestroy()
    }

    abstract fun getLayout(): Int

    inner class ViewLifecycle {
        private val viewRelatedFunctions = arrayListOf<() -> Unit>()
        private var isViewCreated = false

        fun runWhenViewCreated(function: () -> Unit) {
            if (isViewCreated) function()
            else viewRelatedFunctions.add(function)
        }

        internal fun onViewCreated() {
            isViewCreated = true
            viewRelatedFunctions.forEach { it() }
            viewRelatedFunctions.clear()
        }

        internal fun destroy() {
            isViewCreated = false
        }
    }
}