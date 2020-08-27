package me.pitok.login.viewmodels

import androidx.lifecycle.ViewModel
import me.pitok.login.state.LoginViewState
import me.pitok.sdkextentions.isValidPassword

class LoginViewModel: ViewModel() {

    companion object{
        const val USERNAME_FIELD_SWITCH = "username_field_switch"
        const val PASSWORD_FIELD_SWITCH = "password_field_switch"
        const val CONFIRM_PASSWORD_FIELD_SWITCH = "confirm_password_field_switch"
    }

    var viewState: LoginViewState = LoginViewState()

    fun onUsernameChangedListener(value: String){
        viewState = viewState.copy(
            isPositiveButtonEnabled =
            if (value.isEmpty()){
                viewState
                    .isPositiveButtonEnabled
                    .switchOff(USERNAME_FIELD_SWITCH)
            }else{
                viewState
                    .isPositiveButtonEnabled
                    .switchOn(USERNAME_FIELD_SWITCH)
            })
    }

    fun onPasswordChangedListener(value: String){
        viewState = viewState.copy(
            isPositiveButtonEnabled =
            if (value.isValidPassword()){
                viewState
                    .isPositiveButtonEnabled
                    .switchOff(PASSWORD_FIELD_SWITCH)
            }else{
                viewState
                    .isPositiveButtonEnabled
                    .switchOn(PASSWORD_FIELD_SWITCH)
            },
            isNegativeButtonEnabled =
                if (value.isValidPassword()){
                    viewState.isNegativeButtonEnabled
                        .switchOn(PASSWORD_FIELD_SWITCH).apply {
                        if (value == viewState.lastConfirmPassword){
                            switchOn(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }else{
                            switchOff(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }
                    }
                }else {
                    viewState.isNegativeButtonEnabled
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
            isNegativeButtonEnabled =
                viewState.isNegativeButtonEnabled.apply {
                        if (value == viewState.lastPassword){
                            switchOn(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }else{
                            switchOff(CONFIRM_PASSWORD_FIELD_SWITCH)
                        }
                    },
            lastConfirmPassword = value
        )
    }
}