package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import be.technifutur.devmob9.sdsftryone.model.*
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

class DbManager {

    companion object {
        lateinit var instance: DbManager private set
        private lateinit var context: Context
        private lateinit var dateTimeFormatter: SimpleDateFormat

        fun init(context: Context) {
            instance = DbManager()
            DbManager.context = context
            dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            Realm.init(context)
        }

        fun findClub(code: String): ClubData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(ClubData::class.java)
                .equalTo("code", code)
                .findFirst()
        }

        fun findChamp(id: Int): ChampData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(ChampData::class.java)
                .equalTo("id", id)
                .findFirst()
        }

        fun findDay(id: Int, champ: Int): DayData? {
            return findChamp(champ)?.days?.firstOrNull { it.id ==id }
        }

        fun findMatch(id: Int, day: Int, champ: Int): MatchData? {
            return findDay(day, champ)?.matches?.firstOrNull {it.id == id}
        }

        fun findPlayer(id: Int): PlayerData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(PlayerData::class.java)
                .equalTo("id", id)
                .findFirst()
        }

        fun findEvent(id: Int, match: Int, day: Int, champ: Int): EventData? {
           return findMatch(match, day, champ)?.events?.firstOrNull { it.id == id }

        }

        fun findMatchPlayer(id: Int, match: Int, day: Int, champ: Int): MatchPlayerData? {
            return findChamp(champ)?.days?.firstOrNull {
                it.id == day
            }?.matches?.firstOrNull { it.id == match }?.players?.firstOrNull { it.id == id }
        }

        fun addClub(code: String, shortName: String, fullName: String, logo: String?): ClubData? {
            val realm = Realm.getDefaultInstance()
            var club: ClubData?
            try {
                club = realm.createObject(ClubData::class.java, code)

            } catch (e: RealmException) {
                club = findClub(code)
            }
            club?.let {
                club.shortName = shortName
                club.fullName = fullName
                club.logo = logo
            }

            return club
        }

        fun addChampTeam(team: String, code: String): ChampTeamData? {
            val realm = Realm.getDefaultInstance()

            try {
                val champTeam = realm.createObject(ChampTeamData::class.java)
                champTeam.team = team
                champTeam.code = code
                return champTeam
            } catch (e: RealmException) {
                Log.d("DBMANAGER", e.message ?: "addChampTeam")
            }
            return null
        }

        fun addChamp(
            id: Int, name: String, numDay: Int, season: Int, matchConfig: String,
            genForfeit: List<String>, teams: List<ChampTeamData>
        ): ChampData? {
            val realm = Realm.getDefaultInstance()
            var champ: ChampData?

            try {
                champ = realm.createObject(ChampData::class.java, id)
            } catch (e: RealmException) {
                champ = findChamp(id)
            }

            champ?.let {
                champ.name = name
                champ.numDay = numDay
                champ.season = season
                champ.matchConfig = matchConfig
                champ.teams.addAll(teams)
                champ.genForfeit.addAll(genForfeit)
            }

            return champ
        }

        fun addDay(
            id: Int, champ: Int, name: String, date: Date,
            comment: String
        ): DayData? {

            val realm = Realm.getDefaultInstance()
            var day: DayData?

            try {
                day = realm.createObject(DayData::class.java)

            } catch (e: RealmException) {
                day = findDay(id, champ)
            }

            day?.let {

                day.name = name
                day.date = date
                day.comment = comment
                val champData = findChamp(champ)
                champData?.days?.add(day)
            }

            return day

        }

        fun addMatch(
            id: Int, day: Int, champ: Int, date: Date?, hour: String, homeTeam: String,
            awayTeam: String, homeScore: Int?, awayScore: Int?, comment: String?, locked: String?
        ): MatchData? {

            val realm = Realm.getDefaultInstance()
            var match: MatchData?

            try {
                match = realm.createObject(MatchData::class.java)

            } catch (e: RealmException) {
                Log.d("DBMANAGER", e.message ?: "addMatch")
                match = findMatch(id, day, champ)
            }

            match?.let {
                match.date = date
                match.hour = hour
                match.homeTeam = homeTeam
                match.awayTeam = awayTeam
                match.homeScore = homeScore
                match.awayScore = awayScore
                match.comment = comment ?: ""
                match.locked = locked

                val matchDay = findDay(day, champ)
                matchDay?.matches?.add(match)
            }
            return match
        }

        fun addPlayer(
            id: Int, firstName: String, lastName: String,
            number: Int, team: List<String>
        ): PlayerData? {

            val realm = Realm.getDefaultInstance()
            var player: PlayerData?

            try {
                player = realm.createObject(PlayerData::class.java, id)
            } catch (e: RealmException) {
                player = findPlayer(id)
                Log.d("DBMANAGER", e.message ?: "addPlayer")
            }

            player?.let {
                player.firstName = firstName
                player.lastname = lastName
                player.number = number
                player.team.addAll(team)
            }

            return player
        }

        fun addEvent(
            id: Int, match: Int, day: Int, champ: Int, time: String,
            team: String, type: String, param: String
        ): EventData? {

            val realm = Realm.getDefaultInstance()
            var event: EventData?

            try {
                event = realm.createObject(EventData::class.java)
            } catch (e: RealmException) {
                event = findEvent(id, match, day, champ)
                Log.d("DBMANAGER", e.message ?: "addEventt")
            }

            event?.let {
                event.time = time
                event.team = team
                event.type = type
                event.team = team
                event.param = param
            }

            return event
        }

        fun addMatchPlayer(
            id: Int, match: Int, day: Int, champ: Int,
            player: Int, name: String, number: Int,
            status: Int
        ): MatchPlayerData? {
            val realm = Realm.getDefaultInstance()
            var matchPlayer: MatchPlayerData?

            try {
                matchPlayer = realm.createObject(MatchPlayerData::class.java)
            } catch (e: RealmException) {
                matchPlayer = findMatchPlayer(id, match, day, champ)
            }

            matchPlayer?.let {
                matchPlayer.player = findPlayer(player)
                matchPlayer.name = name
                matchPlayer.number = number
                matchPlayer.status = status
            }

            return matchPlayer
        }

        fun removeClub(code: String) {
            findClub(code)?.let { removeClub(it) }
        }

        fun removeClub(clubData: ClubData) {
            clubData.delete()
        }

        fun removeChamp(id: Int) {
            findChamp(id)?.let { removeChamp(it) }
        }

        fun removeChamp(champData: ChampData) {
            champData.delete()
        }

        fun removeDay(id: Int, champ: Int) {
            findDay(id, champ)?.let { removeDay(it) }
        }

        fun removeDay(day: DayData) {
            day.delete()
        }

        fun removeMatch(id: Int, day: Int, champ: Int) {
            findMatch(id, day, champ)?.let { removeMatch(it) }
        }

        fun removeMatch(match: MatchData) {
            match.delete()
        }

        fun removePlayer(id: Int) {
            findPlayer(id)?.delete()
        }

        fun removeEvent(id: Int, match: Int, day: Int, champ: Int) {
            findEvent(id, match, day, champ)?.let { removeEvent(it) }
        }

        fun removeEvent(event: EventData) {
            event.delete()
        }

        fun removeMatchPlayer(id: Int, match: Int, day: Int, champ: Int) {
            findMatchPlayer(id, match, day, champ)?.let { removeMatchPlayer(it) }
        }

        fun removeMatchPlayer(matchPlayer: MatchPlayerData) {
            matchPlayer.delete()
        }

        fun getTableUpdateTime(): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val lastDbUpdateTimeStr = preferences.getString("db_update_time", "")
            return if (lastDbUpdateTimeStr?.isBlank() != false) {
                null
            } else {
                lastDbUpdateTimeStr
            }
        }

        fun startUpdate() {
            Realm.getDefaultInstance().beginTransaction()
        }

        fun endUpdate() {
            Realm.getDefaultInstance().commitTransaction()
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("db_update_time", dateTimeFormatter.format(Date()))
            editor.apply()
        }

        fun getAllChampData(): List<ChampData> {
            val realm = Realm.getDefaultInstance()
            return realm.where<ChampData>().findAll()
        }

        fun getCalendar(team: String): List<MatchData> {
            val matches = arrayListOf<MatchData>()

            getAllChampData().forEach { champ ->
                val teamInfos = champ.teams.where()
                    .equalTo("team", team)
                    .findFirst()

                teamInfos?.let {
                    var prefTeam = teamInfos.code.substring(teamInfos.code.length-1)
                    if ("ABC".indexOf(prefTeam) < 0 ) {
                        prefTeam = ""
                    }
                    champ.days.forEach { day ->
                        if (day.id >= teamInfos.firstDay) {
                            day.getMyClubMatch(prefTeam)?.let { match ->
                                matches.add(match)
                            }
                        }
                    }
                }
            }

            matches.sortWith(kotlin.Comparator { m1, m2  ->
                val d1 = m1.getMatchDate()
                val d2 = m2.getMatchDate()
                if (d1 == d2){
                    return@Comparator m1.hour.compareTo(m2.hour)
                }
                return@Comparator d1.compareTo(d2)
            })

            return matches
        }

    } // fin companion Object


    fun getAllClubData(): List<ClubData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<ClubData>().findAll()
    }

    fun getAllDayData(): List<DayData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<DayData>().findAll()
    }

    fun getAlltMatchData(): List<MatchData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchData>().findAll()
    }

    fun getAllEventData(): List<EventData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<EventData>().findAll()
    }

    fun getAllMatchPlayerData(): List<MatchPlayerData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchPlayerData>().findAll()
    }

    fun getallPlayerData(): List<PlayerData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<PlayerData>().findAll()
    }
}