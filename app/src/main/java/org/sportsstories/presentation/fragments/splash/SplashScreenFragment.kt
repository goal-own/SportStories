package org.sportsstories.presentation.fragments.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.sportsstories.R
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.viewmodel.SplashScreenViewModel

class SplashScreenFragment : BaseFragment(
        R.layout.fragment_splash
) {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
    }

    companion object {

        fun newInstance() = SplashScreenFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.isSessionIdActiveEvent.value?.data?.let { isSessionActive ->
            viewModel.openLoginOrMainScreen(isSessionActive)
        }
    }

    private fun initObservers() {
        viewModel.isSessionIdActiveEvent.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> viewModel.openLoginOrMainScreen(event.data)
                is ContentEvent.Error -> viewModel.openLoginOrMainScreen(false)
            }
        })
    }


}
