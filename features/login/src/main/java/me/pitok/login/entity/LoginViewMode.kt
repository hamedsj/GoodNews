package me.pitok.login.entity

sealed class LoginViewMode {

    object LoginMode: LoginViewMode()
    object SignUpMode: LoginViewMode()

}