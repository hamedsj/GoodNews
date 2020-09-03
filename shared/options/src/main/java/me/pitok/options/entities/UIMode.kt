package me.pitok.options.entities

sealed class UIMode {
    object DarkMode : UIMode()

    object LightMode : UIMode()

    companion object{
        var currentUIMode: UIMode = LightMode
    }
}