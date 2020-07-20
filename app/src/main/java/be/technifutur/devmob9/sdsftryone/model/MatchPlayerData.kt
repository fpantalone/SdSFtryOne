package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class MatchPlayerData(
    @Index
    var id: Int = 0,
    var player: PlayerData? = null,
    var name: String = "",
    var number: Int = 0,
    var status: Int = 0,
    @LinkingObjects("players")
    val match: RealmResults<MatchData>? = null
) : RealmObject()