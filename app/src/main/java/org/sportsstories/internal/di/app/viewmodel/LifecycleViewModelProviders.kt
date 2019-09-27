package org.sportsstories.internal.di.app.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

object LifecycleViewModelProviders {

    fun of(
            lifecycleOwner: LifecycleOwner,
            factory: ViewModelProvider.Factory = getViewModelFactory(lifecycleOwner)
    ) = when (lifecycleOwner) {
        is Fragment -> ViewModelProviders.of(lifecycleOwner, factory)
        is FragmentActivity -> ViewModelProviders.of(lifecycleOwner, factory)
        else -> throw IllegalArgumentException("Not supported LifecycleOwner : ${lifecycleOwner::class.java.name}")
    }

    private fun getViewModelFactory(provider: Any): ViewModelProvider.Factory =
            when (provider) {
                is ProviderHolder -> provider.viewModelFactory
                is Fragment -> getViewModelFactory(provider.parentFragment ?: provider.requireActivity())
                is FragmentActivity -> getViewModelFactory(provider.application)
                else -> throw IllegalArgumentException("View model factory not found.")
            }

}
