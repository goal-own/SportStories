package org.sportsstories.extensions

import android.content.Context
import org.joda.time.LocalDate
import org.sportsstories.R

fun LocalDate.toDateString(context: Context): String {
    return when (LocalDate.now()) {
        this -> context.getString(R.string.global_today)
        plusDays(1) -> context.getString(R.string.global_tomorrow)
        minusDays(1) -> context.getString(R.string.global_yesterday)
        else -> context.getString(R.string.day_with_month, dayOfMonth().getAsShortText(ruLocale), monthOfYear().getAsShortText(ruLocale))
    }
}
