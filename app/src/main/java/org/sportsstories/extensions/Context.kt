package org.sportsstories.extensions

import android.content.Context
import android.content.Intent

/**
 * Определяет существует ли Activity для заданного Intent
 */
fun Context.canLaunchIntent(intent: Intent): Boolean {
    val infos = packageManager.queryIntentActivities(intent, 0)
    return (infos != null && infos.size != 0)
}
