package me.pitok.login.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.coroutines.Dispatcher
import me.pitok.login.R
import me.pitok.login.api.requests.LoginRequest
import me.pitok.login.api.requests.SignUpRequest
import me.pitok.login.datasource.LoginWritable
import me.pitok.login.datasource.SignUpWritable
import me.pitok.login.entity.LoginViewMode
import me.pitok.login.errors.LoginExceptionContext
import me.pitok.login.state.LoginViewState
import me.pitok.login.views.LoginFragment
import me.pitok.navigation.Navigate
import me.pitok.networking.CommonExceptions
import me.pitok.networking.ifSuccessful
import me.pitok.networking.otherwise
import me.pitok.sdkextentions.isValidPassword
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginWritable: LoginWritable,
                                         private val signupWritable: SignUpWritable,
                                         private val dispatchers: Dispatcher,
                                         @ApplicationContext private val context: Context): ViewModel() {

    companion object{
        const val USERNAME_FIELD_SWITCH = "username_field_switch"
        const val PASSWORD_FIELD_SWITCH = "password_field_switch"
        const val CONFIRM_PASSWORD_FIELD_SWITCH = "confirm_password_field_switch"
    }

    var viewState: LoginViewState = LoginViewState()

    private val pShowLoadingObservable = MutableLiveData<Boolean>()
    val showLoadingObserver: LiveData<Boolean> = pShowLoadingObservable

    private val pNavigationObservable = MutableLiveData<Navigate>()
    val navigationObserver: LiveData<Navigate> = pNavigationObservable

    private val pShowMessageObservable = MutableLiveData<String>()
    val showMessageObserver: LiveData<String> = pShowMessageObservable

    private val pChangeViewModeObservable = MutableLiveData<LoginViewMode>()
    val changeViewModeObserver: LiveData<LoginViewMode> = pChangeViewModeObservable


    fun onUsernameChangedListener(value: String){
        viewState = viewState.copy(
            isLoginButtonEnabled =
            if (value.isEmpty()){
                viewState
                    .isLoginButtonEnabled
                    .switchOff(USERNAME_FIELD_SWITCH)
            }else{
                viewState
                    .isLoginButtonEnabled
                    .switchOn(USERNAME_FIELD_SWITCH)
            },
            lastUsername = value
        )
    }

    fun onPasswordChangedListener(value: String){
        viewState = viewState.copy(
            isLoginButtonEnabled =
            if (value.isValidPassword()){
                viewState
                    .isLoginButtonEnabled
                    .switchOff(PASSWORD_FIELD_SWITCH)
            }else{
                viewState
                    .isLoginButtonEnabled
                    .switchOn(PASSWORD_FIELD_SWITCH)
            },
            isSignUpButtonEnabled =
                if (value.isValidPassword()){
                    viewState.isSignUpButtonEnabled
                        .switchOn(PASSWORD_FIELD_SWITCH).apply {
                        if (value == viewState.lastConfirmPassword){
                            switchOn(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }else{
                            switchOff(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }
                    }
                }else {
                    viewState.isSignUpButtonEnabled
                        .switchOff(PASSWORD_FIELD_SWITCH).apply {
                            if (value == viewState.lastConfirmPassword) {
                                switchOn(CONFIRM_PASSWORD_FIELD_SWITCH)
                            } else {
                                switchOff(CONFIRM_PASSWORD_FIELD_SWITCH)
                            }
                        }
                },
            lastPassword = value
        )
    }

    fun onConfirmPasswordChangedListener(value: String){
        viewState = viewState.copy(
            isSignUpButtonEnabled =
                viewState.isSignUpButtonEnabled.apply {
                        if (value == viewState.lastPassword){
                            switchOn(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }else{
                            switchOff(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }
                    },
            lastConfirmPassword = value
        )
    }

    fun onPositiveBtClick(view : View){
        when(viewState.viewMode){
            is LoginViewMode.LoginMode -> {
                if (viewState.isLoginButtonEnabled.value){
                    pShowLoadingObservable.value = true
                    viewModelScope.launch(dispatchers.io) {
                        sendLoginRequest()
                    }
                }else{
                    pShowMessageObservable.value =
                        when {
                            viewState.lastUsername.isEmpty() -> {
                                "لطفا نام کاربری را به درستی وارد کنید"
                            }
                            viewState.lastPassword.isValidPassword().not() -> {
                                "کلمه عبور باید حداقل شامل ۶ کاراکتر باشد"
                            }
                            else -> {
                                "لطفا اطلاعات را به درستی وارد کنید"
                            }
                        }
                }
            }
            is LoginViewMode.SignUpMode -> {
                if (viewState.isSignUpButtonEnabled.value){
                    pShowLoadingObservable.value = true
                    viewModelScope.launch(dispatchers.io) {
                        sendSignupRequest()
                    }
                }else{
                    pShowMessageObservable.value =
                        when {
                            viewState.lastUsername.isEmpty() -> {
                                "لطفا نام کاربری را به درستی وارد کنید"
                            }
                            viewState.lastPassword.isValidPassword().not() -> {
                                "کلمه عبور باید حداقل شامل ۶ کاراکتر باشد"
                            }
                            viewState.lastConfirmPassword != viewState.lastPassword -> {
                                "کلمات عبور با هم تطابق ندارند"
                            }
                            else -> {
                                "لطفا اطلاعات را به درستی وارد کنید"
                            }
                        }
                }
            }
        }
    }

    fun onNegativeBtClick(view : View){
        viewModelScope.launch {
            delay(LoginFragment.CLICK_ANIMATION_DURATION)
            when(viewState.viewMode){
                is LoginViewMode.LoginMode -> {
                    viewState = viewState.copy(viewMode = LoginViewMode.SignUpMode)
                    pChangeViewModeObservable.value = LoginViewMode.SignUpMode
                }
                is LoginViewMode.SignUpMode -> {
                    viewState = viewState.copy(viewMode = LoginViewMode.LoginMode)
                    pChangeViewModeObservable.value = LoginViewMode.LoginMode
                }
            }
        }
    }

    private suspend fun sendSignupRequest(){
        signupWritable
            .write(
                SignUpRequest(
                    viewState.lastUsername,
                    viewState.lastPassword)
            )
            .ifSuccessful {
                if (it){
                    pShowLoadingObservable.value = false
                    pShowMessageObservable.value = "اطلاعات شما با موفقیت ثبت شد =)"
                    pNavigationObservable.value =
                        Navigate.ToDeepLink(
                            context.resources.getString(R.string.deeplink_home)
                        )
                }
            }.otherwise {throwable ->
                pShowLoadingObservable.value = false
                pShowMessageObservable.value =
                    when (throwable) {
                        is CommonExceptions.ConnectionException -> {
                            "اینترنت شما قطع می‌باشد =("
                        }
                        is LoginExceptionContext.OldUsername -> {
                            "این نام کاربری قبلا توسط شخص دیگری استفاده شده =("
                        }
                        is LoginExceptionContext.BadPassword -> {
                            "کلمه عبور باید حداقل شامل ۶ کاراکتر باشد"
                        }
                        else -> {
                            "خطا در ارسال اطلاعات؛ لطفا دوباره تلاش کنید"
                        }
                    }
            }
    }

    private suspend fun sendLoginRequest(){
        loginWritable
            .write(LoginRequest(
                viewState.lastUsername,
                viewState.lastPassword))
            .ifSuccessful {
                if (it){
                    pShowLoadingObservable.value = false
                    pShowMessageObservable.value = "با موفقیت وارد شدید =)"
                    pNavigationObservable.value =
                        Navigate.ToDeepLink(
                            context.resources.getString(R.string.deeplink_home)
                        )
                }
            }.otherwise {throwable ->
                pShowLoadingObservable.value = false
                pShowMessageObservable.value =
                    if (throwable is CommonExceptions.ConnectionException){
                        "اینترنت شما قطع می‌باشد =("
                    }else{
                        "خطا در ارسال اطلاعات؛ لطفا دوباره تلاش کنید"
                    }
            }
    }

}