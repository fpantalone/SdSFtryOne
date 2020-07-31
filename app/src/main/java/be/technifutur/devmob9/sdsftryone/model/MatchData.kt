package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.tools.*
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Ignore
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import java.time.LocalDate
import java.util.*

open class MatchData(
    @Index var id: Int = 0,
    var date: Date? = null,
    var hour: String = "",
    var homeTeam: String = "",
    var awayTeam: String = "",
    var homeScore: Int? = null,
    var awayScore: Int? = null,
    var comment: String = "",
    var locked: String? = null,
    var events: RealmList<EventData> = RealmList(),
    var players: RealmList<MatchPlayerData> = RealmList(),
    @LinkingObjects("matches")
    val day: RealmResults<DayData>? = null
) : RealmObject() {

    @Ignore
    val unknownPlayerId = 0
    @Ignore
    val opponentPlayerId = 200

    // TODO: voir code swift sur Git
    // si la date est null il faut aller chercher celle de la journée

    fun getMatchDate(): Date {
        return date ?: day?.firstOrNull()?.date ?: Date()
    }

    fun getNbTitular(): Int {
        return players.filter {
            it.isTitular()
        }.size
    }

    fun getNbCaptain(): Int {
        return players.filter { it.isCaptain() }.size
    }

    fun getNbKeeper(): Int {
        return players.filter { it.isKeeper() }.size
    }

    fun getComment(): MatchComment {
        return MatchComment.createFrom(comment)!!
    }

    fun getLockStatus(): LockStatus? {
        return locked?.let { LockStatus.valueOf(it) }
    }

    fun getEvents(type: EventType?): List<EventData>? {

        type?.jsonString?.let { jsonType ->
            return events.filter { it.type == jsonType }.sortedBy { -it.id }
        }
        return events.sortedBy { -it.id }
    }

    fun getMySide(): TeamSide? {

        val champ = day?.first()?.champ?.first()
        val teamList = champ?.teams?.map {
            it.code
        }
        if (teamList != null) {
            if (teamList.contains(homeTeam)) {
                return TeamSide.HOME
            }
            if (teamList.contains(awayTeam)) {
                return TeamSide.AWAY
            }
        }
        return null
    }

    fun getTeam(side: TeamSide): ClubData {
        var clubCode = if (side == TeamSide.HOME) {
            homeTeam
        } else {
            awayTeam
        }
        var suffix = ""
        val regex = Regex("^(#\\d{5})([ABC])$")

        regex.matchEntire(clubCode)?.let {
            clubCode = it.groupValues[1]
            suffix = it.groupValues[2]
        }

        DbManager.findClub(clubCode)?.let {
            it.suffix = suffix
            return it
        }
        val club = ClubData()
        club.shortName = clubCode
        club.fullName = clubCode

        return club
    }

    fun getPlayer(id: Int): MatchPlayerData? {

        when (id) {
            unknownPlayerId -> {
                val player = MatchPlayerData()
                player.id = id
                player.name = "non attribué^niet toegeschreven^not attribuated"
                return player
            }
            opponentPlayerId -> {
                val player = MatchPlayerData()
                player.id = id
                val mySide = getMySide() ?: return null

                player.name = getTeam(mySide.opponentSide).shortName
                return player
            }
            else -> {
                return players.firstOrNull {
                    it.id == id
                }
            }
        }

    }

    fun getWinner(): TeamSide? {
        val hScore = homeScore ?: return null
        val aScore = awayScore ?: return null
        val comment = getComment()

        when (comment.status) {
            MatchStatus.NONE -> {
                var hGoal = hScore
                var aGoal = aScore

                if (hScore == aScore && day?.first()?.getMatchConfig()?.isEliminatory == true) {
                    val hpenalty = comment.homePenalty
                    val aPenalty = comment.awayPenalty
                    if (null != hpenalty && null != aPenalty) {
                        hGoal = hpenalty
                        aGoal = aPenalty
                    }
                }
                if (hGoal > aGoal) {
                    return TeamSide.HOME
                } else if (hGoal < aGoal) {
                    return TeamSide.AWAY
                }
                return null
            }
            MatchStatus.FORFEIT -> {
                if (5 == hScore) {
                    return TeamSide.HOME
                }
                if (5 == aScore) {
                    return TeamSide.AWAY
                }
                return null
            }
            else -> {
                return null
            }
        }
    }

    fun isInWeek (): Boolean {

        val now = GregorianCalendar.getInstance()
        // on met les heures minutes secondes à zéro
        now.set (now.get(Calendar.YEAR),now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        // on prend la date 7 jpurs avant
        val lastWeek = now.clone() as GregorianCalendar
        lastWeek.add(Calendar.DAY_OF_MONTH, -7)
        // on prend la date 6 jours après
        val nextWeek = now.clone() as GregorianCalendar
        nextWeek.add(Calendar.DAY_OF_MONTH,6)
        // on récupère la date du match
        val matchDay = GregorianCalendar()
        matchDay.time = getMatchDate()
        // on regarde si la date est comprise dans l'intervale
        return matchDay.after(lastWeek) && matchDay.before(nextWeek)
    }

    fun delete() {
        events.forEach { it.delete() }
        players.forEach { it.delete() }
        this.deleteFromRealm()
    }
}