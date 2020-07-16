package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import java.util.*

open class MatchData (
    @Index var id: Int = 0,
    var date: Date? = null,
    var hour: String = "",
    var homeTeam: String = "",
    var awayTeam: String = "",
    var homeResult: Int? = null,
    var awayResult: Int? = null,
    var comment: String = "",
    @LinkingObjects ("matches")
    val day: RealmResults<DayData>? = null
) : RealmObject()