package me.pitok.login.views

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_login.*
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.login.R
import me.pitok.login.di.builder.LoginComponentBuilder
import me.pitok.login.entity.LoginViewMode
import me.pitok.login.viewmodels.LoginViewModel
import me.pitok.navigation.observeNavigation
import me.pitok.sdkextentions.setElevationByDp
import javax.inject.Inject

class LoginFragment: Fragment(R.layout.fragment_login) {

    companion object{
        const val CLICK_ANIMATION_DURATION = 100L
        const val CARD_FADE_OUT_ANIMATION_DURATION = 250L
        const val CARD_FADE_IN_ANIMATION_DURATION = 300L
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val loginViewModel : LoginViewModel by viewModels{ viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        LoginComponentBuilder.getComponent().inject(this)
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
        loginPositiveBt.setOnClickListener(loginViewModel::onPositiveBtClick)
        loginNegativeBt.setOnClickListener(loginViewModel::onNegativeBtClick)
        loginViewModel.run {
            navigationObserver.observeNavigation(this@LoginFragment)
            showMessageObserver.observe(viewLifecycleOwner,this@LoginFragment::showMessage)
            changeViewModeObserver.observe(viewLifecycleOwner,this@LoginFragment::changeViewMode)
            showLoadingObserver.observe(viewLifecycleOwner,this@LoginFragment::showLoading)
        }
    }

    private fun showMessage(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean){
        if (show){
            loginPositiveBt.text = ""
            loginPositiveLoading.visibility = View.VISIBLE
        }else{
            loginPositiveBt.text = when(loginViewModel.viewState.viewMode) {
                is LoginViewMode.LoginMode -> resources.getString(R.string.login)
                else -> resources.getString(R.string.signup)
            }
            loginPositiveLoading.visibility = View.GONE
        }
    }

    private fun changeViewMode(viewMode: LoginViewMode){
        val fadeOutAnimator = ValueAnimator
            .ofFloat(loginFieldsRoot.alpha,0f)
            .apply {
                duration = CARD_FADE_OUT_ANIMATION_DURATION
                interpolator = LinearInterpolator()
            }
        fadeOutAnimator.addUpdateListener {
            loginFieldsRoot.alpha = it.animatedValue as Float
        }
        val fadeInAnimator = ValueAnimator
            .ofFloat(0f,1f)
            .apply {
                duration = CARD_FADE_IN_ANIMATION_DURATION
                interpolator = LinearInterpolator()
            }
        fadeInAnimator.addUpdateListener {
            loginFieldsRoot.alpha = it.animatedValue as Float
        }
        fadeOutAnimator.doOnEnd {
            loginFieldsRoot.visibility = View.INVISIBLE
            when (viewMode){
                is LoginViewMode.LoginMode ->{
                    loginConfirmPassword.visibility = View.GONE
                }
                is LoginViewMode.SignUpMode -> {
                    loginConfirmPassword.visibility = View.VISIBLE
                }
            }
            fadeInAnimator.start()
        }
        fadeOutAnimator.start()
    }

}