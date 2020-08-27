package me.pitok.login.state

import me.pitok.login.entity.LoginViewMode
import me.pitok.login.viewmodels.LoginViewModel.Companion.CONFIRM_PASSWORD_FIELD_SWITCH
import me.pitok.login.viewmodels.LoginViewModel.Companion.PASSWORD_FIELD_SWITCH
import me.pitok.login.viewmodels.LoginViewModel.Companion.USERNAME_FIELD_SWITCH
import me.pitok.sdkextentions.CircuitBoolean


data class LoginViewState(
    val isPositiveButtonEnabled: CircuitBoolean = CircuitBoolean(
        switches = mapOf(
            USERNAME_FIELD_SWITCH to false,
            PASSWORD_FIELD_SWITCH to false
        )
    ),
    val isNegativeButtonEnabled: CircuitBoolean = CircuitBoolean(
        switches = mapOf(
            USERNAME_FIELD_SWITCH to false,
            PASSWORD_FIELD_SWITCH to false,
            CONFIRM_PASSWORD_FIELD_SWITCH to false
        )
    ),
    val viewMode: LoginViewMode = LoginViewMode.LoginMode,
    val lastPassword: String = "",
    val lastConfirmPassword: String = ""
)
