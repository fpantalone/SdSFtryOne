package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

open class ChampTeamData (
    var team: String = "",
    var code: String = "",
    @LinkingObjects ("teams")
    val champ: RealmResults<ChampData>? = null
) : RealmObject()