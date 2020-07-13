package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MatchData(
    @PrimaryKey var id: Int = 0,
    var day: DayData = DayData(),
    var date: Date = Date(),
    var homeTeam: String = "",
    var awayTeam: String = "",
    var homeResult: Int = 0,
    var awayResult: Int = 0,
    var comment: String = ""
) : RealmObject()


// faire méthode pour séparer l'heure et la date