package me.pitok.neew.api.request

import me.pitok.neew.NeewAddType

data class AddNeewRequest(
    val content: String,
    val mode: NeewAddType
)