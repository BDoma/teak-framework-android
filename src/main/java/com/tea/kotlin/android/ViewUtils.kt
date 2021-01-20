package com.tea.kotlin.android

import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.tea.kotlin.android.adapters.menu.MenuItemAdapter

fun View.changeVisibility(newVisibility : Int){
    if (visibility != newVisibility) visibility = newVisibility
}

fun TextView.changeText(newText: String){
    if (text.toString() != newText) text = newText
}

fun EditText.changeError(msg: String?){
    if (error != msg){
        error = msg
        error?.let { requestFocus() }
    }
}


fun MenuItem.changeVisibility(visible: Boolean){
    if (isVisible != visible){
        isVisible = visible
    }
}


fun MenuItemAdapter.changeVisibility(visible: Boolean) {
    call { it.changeVisibility(visible) }
}