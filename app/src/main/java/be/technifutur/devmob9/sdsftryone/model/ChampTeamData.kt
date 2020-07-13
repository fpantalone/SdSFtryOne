package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChampTeamData(
    var team: String = "",
    var code: String = ""
) : RealmObject()