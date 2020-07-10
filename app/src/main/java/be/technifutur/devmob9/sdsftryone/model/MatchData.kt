package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MatchData(
    @PrimaryKey var id: Long = 0, var day: Int = 0, var champ: Int = 0,
    var date: String = "", var hour: String = "", var homeTeam: String = "",
    var guestTeam: String = "",var HomeResult: Int = 0, var gestResult:Int=0,
    var comment: String="") : RealmObject()