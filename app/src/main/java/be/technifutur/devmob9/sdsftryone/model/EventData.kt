package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects

open class EventData(
    @Index var id: Int = 0,
    var time: String = ",",
    var team: String = "",
    var type: String = "",
    var param: String = "",
    @LinkingObjects("events")
    val match: RealmResults<MatchData>? = null
) : RealmObject()