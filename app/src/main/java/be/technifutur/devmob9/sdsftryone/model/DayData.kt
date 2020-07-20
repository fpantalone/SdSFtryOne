package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import java.util.*

open class DayData (
    @Index var id: Int = 0,
    var name: String = "",
    var date: Date = Date(),
    var comment: String = "",
    var matches: RealmList<MatchData> = RealmList(),
    @LinkingObjects ("days")
    val champ: RealmResults<ChampData>? = null
) : RealmObject()