package me.pitok.login.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_login.*
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.login.R
import me.pitok.login.viewmodels.LoginViewModel
import me.pitok.sdkextentions.setElevationByDp
import javax.inject.Inject

class LoginFragment: Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val loginViewModel : LoginViewModel by viewModels{ viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        TODO("inject here")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPositiveBt.setElevationByDp(2f)
        loginPositiveLoading.setElevationByDp(3f)
        loginUsername.addTextChangedListener{editable ->
            loginViewModel.onUsernameChangedListener(editable.toString())
        }
        loginPassword.addTextChangedListener{editable ->
            loginViewModel.onPasswordChangedListener(editable.toString())
        }
        loginConfirmPassword.addTextChangedListener{editable ->
            loginViewModel.onConfirmPasswordChangedListener(editable.toString())
        }

    }
}