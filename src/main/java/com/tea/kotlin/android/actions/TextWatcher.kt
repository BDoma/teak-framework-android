package com.tea.kotlin.android.actions

import android.text.Editable

class TextWatcher<Msg>(private val transform: (String) -> Msg) : Action<Msg>(), android.text.TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // NOP
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        for (dispatch in dispatchers) {
            dispatch(transform(s.toString()))
        }
    }

    override fun afterTextChanged(s: Editable?) {
        // NOP
    }
}