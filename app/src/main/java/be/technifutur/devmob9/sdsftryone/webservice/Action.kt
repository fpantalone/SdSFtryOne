package be.technifutur.devmob9.sdsftryone.webservice

import be.technifutur.devmob9.sdsftryone.tools.ActionType

interface Action {
    val action: Char

    fun getAction (): ActionType? {
        return ActionType.createFrom(action)
    }
}