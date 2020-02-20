package se.gustavkarlsson.skylight.android.lib.ui.extensions

import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.ioki.textref.TextRef
import se.gustavkarlsson.skylight.android.lib.ui.R

fun showErrorSnackbar(view: View, message: CharSequence, length: Int): Snackbar {
    return Snackbar.make(view, message, length).showErrorSnackbar()
}

fun showErrorSnackbar(view: View, @StringRes message: Int, length: Int): Snackbar {
    return Snackbar.make(view, message, length).showErrorSnackbar()
}

fun showErrorSnackbar(view: View, message: TextRef, length: Int): Snackbar {
    return Snackbar.make(view, message.resolve(view.context), length).showErrorSnackbar()
}

private fun Snackbar.showErrorSnackbar(): Snackbar {
    return this
        .setErrorColor()
        .disableSwipeToDismiss()
        .apply { show() }
}

private fun Snackbar.setErrorColor(): Snackbar {
    val backgroundColor = context.theme.resolveColor(R.attr.colorError)
    view.setBackgroundColor(backgroundColor)
    val foregroundColor = context.theme.resolveColor(R.attr.colorOnError)
    setTextColor(foregroundColor)
    return this
}

private fun Snackbar.disableSwipeToDismiss(): Snackbar {
    view.viewTreeObserver.addOnPreDrawListener(SnackbarDisableSwipe(view))
    return this
}

private class SnackbarDisableSwipe(private val snackbarView: View) :
    ViewTreeObserver.OnPreDrawListener {
    override fun onPreDraw(): Boolean {
        snackbarView.viewTreeObserver.removeOnPreDrawListener(this)
        val layoutParams = snackbarView.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.behavior = null
        }
        return true
    }
}
