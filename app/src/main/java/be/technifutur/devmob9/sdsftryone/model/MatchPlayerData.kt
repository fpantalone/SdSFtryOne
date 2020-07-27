package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects

open class MatchPlayerData(
    @Index
    var id: Int = 0,
    var player: PlayerData? = null,
    var name: String = "",
    var number: Int = 0,
    var status: Int = 0,
    @LinkingObjects("players")
    val match: RealmResults<MatchData>? = null
) : RealmObject() {

    fun isTitular (): Boolean {
        return 0 != (status and 1)
    }

    fun isKeeper (): Boolean {
        return 0 != (status and 2)
    }

    fun isCaptain (): Boolean {
        return 0 != (status and 4)
    }

    fun getPlayerName ():String {
        return player?.fullName ?: name
    }
}