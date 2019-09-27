package org.sportsstories.presentation.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.fragment_login.fragment_login_button
import org.sportsstories.R
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.viewmodel.MainViewModel
import ru.touchin.extensions.setOnRippleClickListener

class LoginFragment : BaseFragment() {

    companion object {

        fun newInstance() = LoginFragment()

    }

    val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        fragment_login_button.setOnRippleClickListener {
            VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
    }

}
