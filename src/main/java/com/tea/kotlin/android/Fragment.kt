package com.tea.kotlin.android

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tea.kotlin.android.component.TeaComponent
import com.tea.kotlin.android.lifecycles.ViewLifecycle


abstract class Fragment<Model : Any, Msg> : Fragment(), TeaComponent.Impl<Model, Msg> {
    protected val teaComponent = TeaComponent(this)
    protected val viewLifecycle = ViewLifecycle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycle.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        teaComponent.onDestroy()
    }

    abstract fun getLayout(): Int
}