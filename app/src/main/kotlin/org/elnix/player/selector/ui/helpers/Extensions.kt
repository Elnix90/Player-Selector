package org.elnix.player.selector.ui.helpers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.net.toUri
import org.elnix.dragonlauncher.logging.Logging.TAG
import org.elnix.dragonlauncher.logging.logE
import org.elnix.player.selector.R

/**
 * Show a toast message with flexible input types
 * @param message Can be a String, StringRes Int, or null
 * @param duration Toast duration (LENGTH_SHORT or LENGTH_LONG)
 */
fun Context.showToast(
    message: Any?,
    duration: Int = Toast.LENGTH_SHORT
) {
    val context = this
    val handler = Handler(Looper.getMainLooper())
    handler.post {
        try {
            when (message) {
                is String -> {
                    if (message.isNotBlank()) {
                        Toast.makeText(context, message, duration).show()
                    }
                }

                is Int -> {
                    Toast.makeText(context, message, duration).show()
                }

                else -> {
                    // Null or unsupported type, do nothing
                }
            }
        } catch (e: Exception) {
            logE(TAG, e) { "Error while showing toast" }
        }
    }
}


fun Context.copyToClipboard(text: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(getString(R.string.app_name), text)
    clipboardManager.setPrimaryClip(clipData)
    showToast("Copied to clipboard!")
}

fun Context.pasteClipboard(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = clipboard.primaryClip ?: return null
    if (clip.itemCount == 0) return null
    return clip.getItemAt(0).coerceToText(this)?.toString()
}


fun Context.openUrl(url: String) {
    if (url.isEmpty()) return
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = url.toUri()
    startActivity(intent)
}
