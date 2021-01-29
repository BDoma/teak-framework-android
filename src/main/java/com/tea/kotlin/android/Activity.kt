package com.tea.kotlin.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tea.kotlin.android.component.TeaComponent
import com.tea.kotlin.android.lifecycles.ViewLifecycle


abstract class Activity<Model : Any, Msg> : AppCompatActivity(), TeaComponent.Impl<Model, Msg> {
    protected val teaComponent = TeaComponent(this)
    protected val viewLifecycle = ViewLifecycle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent.onCreate()
        setContentView(getLayout())
        viewLifecycle.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        teaComponent.onDestroy()
    }

    abstract fun getLayout(): Int
}