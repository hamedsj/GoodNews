package me.pitok.login.state

import me.pitok.login.entity.LoginViewMode
import me.pitok.sdkextentions.CircuitBoolean


data class LoginViewState(
    val isLoginButtonEnabled: CircuitBoolean = CircuitBoolean(
        switches = mapOf(
            USERNAME_FIELD_SWITCH to false,
            PASSWORD_FIELD_SWITCH to false
        )
    ),
    val isSignUpButtonEnabled: CircuitBoolean = CircuitBoolean(
        switches = mapOf(
            USERNAME_FIELD_SWITCH to false,
            PASSWORD_FIELD_SWITCH to false,
            CONFIRM_PASSWORD_FIELD_SWITCH to false
        )
    ),
    val viewMode: LoginViewMode = LoginViewMode.LoginMode,
    val lastUsername: String = "",
    val lastPassword: String = "",
    val lastConfirmPassword: String = ""
){
    companion object{
        const val USERNAME_FIELD_SWITCH = "username_field_switch"
        const val PASSWORD_FIELD_SWITCH = "password_field_switch"
        const val CONFIRM_PASSWORD_FIELD_SWITCH = "confirm_password_field_switch"
    }
}
