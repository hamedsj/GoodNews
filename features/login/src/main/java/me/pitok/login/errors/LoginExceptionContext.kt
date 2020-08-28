package me.pitok.login.errors

sealed class LoginExceptionContext : Throwable() {

    object OldUsername : LoginExceptionContext()

    object BadPassword : LoginExceptionContext()

}