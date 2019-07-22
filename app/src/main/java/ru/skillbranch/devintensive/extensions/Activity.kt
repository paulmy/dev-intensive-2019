package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.absoluteValue


fun Activity.hideKeyboard() {
    hideKeyboard(this.GetView())
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen():Boolean{
    val rootView = this.GetView().rootView
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val screenRect = Rect()
    this.windowManager.defaultDisplay.getRectSize(screenRect)
    return (rect.height() - screenRect.height()).absoluteValue >100 ||
            (rect.width() - screenRect.width()).absoluteValue >100
}

fun Activity.isKeyboardClosed():Boolean = !this.isKeyboardOpen()

fun Activity.GetView():View = this.currentFocus ?: View(this)