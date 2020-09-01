package me.pitok.goodnews.login

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pitok.coroutines.Dispatcher
import me.pitok.goodnews.R
import me.pitok.goodnews.appMain.MainActivity
import me.pitok.goodnews.login.di.LoginActivityComponentBuilder
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.login.entity.LoginViewMode
import me.pitok.sdkextentions.setElevationByDp
import me.pitok.settings.entity.UIMode
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    companion object{
        const val CLICK_ANIMATION_DURATION = 100L
        const val CARD_FADE_OUT_ANIMATION_DURATION = 250L
        const val CARD_FADE_IN_ANIMATION_DURATION = 300L
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dispatchers: Dispatcher


    private val loginViewModel : LoginViewModel by viewModels{ viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        val userInterfaceMode = when (UIMode.currentUIMode) {
            is UIMode.DarkMode -> AppCompatDelegate.MODE_NIGHT_YES
            is UIMode.LightMode -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(userInterfaceMode)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginActivityComponentBuilder
            .getComponent()
            .inject(this)

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
            showMessageObserver.observe(this@LoginActivity,this@LoginActivity::showMessage)
            changeViewModeObserver.observe(this@LoginActivity,this@LoginActivity::changeViewMode)
            showLoadingObserver.observe(this@LoginActivity,this@LoginActivity::showLoading)
            navigationObserver.observe(this@LoginActivity, this@LoginActivity::goToHome)
        }

    }

    private fun goToHome(it: Boolean){
        Intent(
            this@LoginActivity,
            MainActivity::class.java)
            .run {
                startActivity(this)
                finish()
                overridePendingTransition(
                    R.anim.enter_activity_fade_in_anim,
                    R.anim.enter_activity_fade_out_anim
                )
            }
    }

    private fun showMessage(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean){
        if (show){
            loginPositiveBt.text = ""
            loginPositiveLoading.visibility = View.VISIBLE
        }else{
            loginPositiveBt.text = when(loginViewModel.viewState.viewMode) {
                is LoginViewMode.LoginMode -> resources.getString(me.pitok.login.R.string.login)
                else -> resources.getString(me.pitok.login.R.string.signup)
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
            when (viewMode){
                is LoginViewMode.LoginMode ->{
                    loginConfirmPassword.visibility = View.GONE
                }
                is LoginViewMode.SignUpMode -> {
                    loginConfirmPassword.visibility = View.VISIBLE
                }
            }
            lifecycleScope.launch{
                delay(CARD_FADE_IN_ANIMATION_DURATION)
                withContext(dispatchers.main) {
                    fadeInAnimator.start()
                }
            }
        }
        fadeOutAnimator.start()
    }

}