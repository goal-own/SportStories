package org.sportsstories.internal.routing.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator

open class ScreenNavigator(
        activity: FragmentActivity,
        @IdRes containerId: Int
) : SupportAppNavigator(activity, containerId)
