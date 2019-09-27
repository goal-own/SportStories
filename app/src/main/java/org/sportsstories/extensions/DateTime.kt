package org.sportsstories.extensions

import android.content.Context
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.sportsstories.R
import java.util.Locale

val ruLocale = Locale("ru")
val timeFormatter = DateTimeFormat.forPattern("HH:mm")

fun DateTime.toDateWithMonthAndTimeString(context: Context): String =
        context.getString(R.string.time_format_for_game, toDateString(context), toTimeString(context))

fun DateTime.toDateString(context: Context): String = toLocalDate().toDateString(context)

fun DateTime.toTimeString(context: Context): String = toString(timeFormatter)
