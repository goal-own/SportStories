package org.sportsstories.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import org.sportsstories.R
import org.sportsstories.domain.model.VkUser
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.internal.routing.navigator.RootNavigator
import org.sportsstories.viewmodel.LoginViewModel
import org.sportsstories.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private val loginViewModel by lazy {
        LifecycleViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private val navigator by lazy {
        RootNavigator(this, R.id.activity_main_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.openSplash()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        viewModel.navigatorHolder.removeNavigator()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                loginViewModel.onLogin(
                        VkUser(
                                token.userId ?: -1,
                                token.accessToken
                        )
                )
            }

            override fun onLoginFailed(errorCode: Int) {
                // Do nothing
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
