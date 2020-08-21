package me.pitok.neew

sealed class NeewAddType{
    object ByContent : NeewAddType()
    object ByLink : NeewAddType()
}