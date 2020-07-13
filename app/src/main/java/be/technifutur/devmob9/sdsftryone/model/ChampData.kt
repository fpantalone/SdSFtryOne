package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChampData constructor(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var numDay: Int = 0,
    var season: Int = 0,
    var teams: RealmList<ChampTeamData> = RealmList(),
    var generalForfeitTeams: RealmList<String> = RealmList(),
    var days: RealmList<DayData> = RealmList()
) : RealmObject()