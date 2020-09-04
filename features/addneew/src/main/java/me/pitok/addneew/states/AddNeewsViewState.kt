package me.pitok.addneew.states

import me.pitok.neew.NeewAddType

data class AddNeewsViewState(val addNeewType: NeewAddType = NeewAddType.ByContent,
                             val isSendButtonEnabled: Boolean = false,
                             val lastLinkEntered: String = "",
                             val lastContentEntered: String = ""
)
