package beprogressive.common

import android.content.Context
import android.text.Spanned
import android.view.View
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.markWithAccentColor(text: String): String {
    val accentColor = Integer.toHexString(getColor(R.color.orange) and 0x00ffffff)
    return "<font color='#$accentColor'>$text</font>"
}

fun spannedText(text: String): Spanned {
    return HtmlCompat.fromHtml(
        text,
        HtmlCompat.FROM_HTML_MODE_COMPACT
    )
}

fun Context.createColoredDialog(
    view: View? = null,
    title: String? = null,
    message: Spanned,
    layoutId: Int? = null,
    icon: Int? = null,
    @StringRes submitText: Int = R.string.ok,
    onDismiss: (() -> Unit)? = null,
    onSubmit: () -> Unit,
): MaterialAlertDialogBuilder {
    val builder = MaterialAlertDialogBuilder(this)
    if (view != null) builder.setView(view)
    if (layoutId != null) builder.setView(layoutId)
    if (icon != null) builder.setIcon(icon)
    if (title != null) builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(submitText) { _, _ ->
        onSubmit()
    }
    builder.setOnDismissListener {
        onDismiss?.invoke()
    }

    return builder
}