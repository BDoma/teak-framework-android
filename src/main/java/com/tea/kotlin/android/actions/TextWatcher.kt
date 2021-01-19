package com.tea.kotlin.android.actions

import android.text.Editable
import com.tea.kotlin.android.common.ActionLifecycle

class TextWatcher<Msg>(lifecycleOwner: ActionLifecycle<Msg>, private val transform: (String) -> Msg) : Action<Msg>(lifecycleOwner), android.text.TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // NOP
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        dispatch(transform(s.toString()))
    }

    override fun afterTextChanged(s: Editable?) {
        // NOP
    }
}