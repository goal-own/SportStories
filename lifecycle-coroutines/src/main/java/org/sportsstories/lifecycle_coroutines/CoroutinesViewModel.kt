package org.sportsstories.lifecycle_coroutines

import androidx.lifecycle.ViewModel

open class CoroutinesViewModel(
    private val destroyable: Destroyable = BaseDestroyable(),
    private val liveDateDispatcher: LiveDateDispatcher = BaseLiveDataDispatcher(destroyable)
) : ViewModel(), Destroyable by destroyable, LiveDateDispatcher by liveDateDispatcher {

    override fun onCleared() {
        super.onCleared()
        destroyable.onDestroy()
    }

}
