package me.pitok.settings.entity

sealed class NotifsCount {
    object None : NotifsCount()

    object EveryHour : NotifsCount()

    object Every3Hours : NotifsCount()

    object Every6Hours : NotifsCount()

    object Every12Hours : NotifsCount()
}