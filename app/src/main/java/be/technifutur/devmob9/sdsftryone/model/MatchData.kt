package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.tools.EventType
import be.technifutur.devmob9.sdsftryone.tools.MatchComment
import be.technifutur.devmob9.sdsftryone.tools.TeamSide
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import java.util.*

open class MatchData(
    @Index var id: Int = 0,
    var date: Date? = null,
    var hour: String = "",
    var homeTeam: String = "",
    var awayTeam: String = "",
    var homeResult: Int? = null,
    var awayResult: Int? = null,
    var comment: String = "",
    var locked: String? = null,
    var events: RealmList<EventData> = RealmList(),
    var players: RealmList<MatchPlayerData> = RealmList(),
    @LinkingObjects("matches")
    val day: RealmResults<DayData>? = null
) : RealmObject() {

    // TODO: voir code swift sur Git
    // si la date est null il faut aller chercher celle de la journée

    fun getMatchDate ():Date {
        return date ?: day?.firstOrNull()?.date ?: Date()
    }

    fun getNbTitular ():Int {
        return players.filter {
           it.isTitular()
        }.size
    }

    fun getNbCaptain (): Int {
        return players.filter { it.isCaptain() }.size
    }

    fun getNbKeeper (): Int {
        return players.filter { it.isKeeper() }.size
    }

    fun getComment (): MatchComment {
        return
    }

    fun getEvents (): EventType {
        // return tablea eventData
        // retourne tout les events
        // tri inverse
    }

    fun getMySide (): TeamSide {

    }
            // compare hometype

}