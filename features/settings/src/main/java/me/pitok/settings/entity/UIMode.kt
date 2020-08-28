package me.pitok.settings.entity

sealed class UIMode {
    object DarkMode : UIMode()

    object LightMode : UIMode()
}