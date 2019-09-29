package org.sportsstories.presentation.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.sportsstories.R
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.viewmodel.SplashScreenViewModel

class SplashScreenFragment : Fragment() {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
    }

    companion object {

        fun newInstance() = SplashScreenFragment()

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

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
