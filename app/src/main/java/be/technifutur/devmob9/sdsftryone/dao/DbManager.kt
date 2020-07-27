package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import be.technifutur.devmob9.sdsftryone.model.*
import io.realm.Case
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
            val realm = Realm.getDefaultInstance()
            return realm.where(DayData::class.java)
                .equalTo("id", id)
                .equalTo("champ.id", champ)
                .findFirst()
        }

        fun findMatch(id: Int, day: Int, champ: Int): MatchData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(MatchData::class.java)
                .equalTo("id", id)
                .equalTo("day.id", day)
                .equalTo("day.champ.id", champ)
                .findFirst()
        }

        fun findPlayer(id: Int): PlayerData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(PlayerData::class.java)
                .equalTo("id", id)
                .findFirst()
        }

        fun findEvent(id: Int, match: Int, day: Int, champ: Int): EventData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(EventData::class.java)
                .equalTo("id", id)
                .equalTo("match", match)
                .equalTo("day", day)
                .equalTo("champ", champ)
                .findFirst()
        }

        fun findMatchPlayer(id: Int, match: Int, day: Int, champ: Int): MatchPlayerData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(MatchPlayerData::class.java)
                .equalTo("id", id)
                .equalTo("match", match)
                .equalTo("day", day)
                .equalTo("champ", champ)
                .findFirst()
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
                day = realm.createObject(DayData::class.java, id)

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
                match = realm.createObject(MatchData::class.java, id)

            } catch (e: RealmException) {
                Log.d("DBMANAGER", e.message ?: "addMatch")
                match = findMatch(id, day, champ)
            }

            match?.let {
                match.date = date
                match.hour = hour
                match.homeTeam = homeTeam
                match.awayTeam = awayTeam
                match.homeResult = homeScore
                match.awayResult = awayScore
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
                event = realm.createObject(EventData::class.java, id)
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
                matchPlayer = realm.createObject(MatchPlayerData::class.java, id)
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
            val club = findClub(code)
            club?.deleteFromRealm()
        }

        fun removeChamp(id: Int) {
            val champ = findChamp(id)
            champ?.teams?.deleteAllFromRealm()
            for (day in champ?.days ?: listOf<DayData>()) {
                removeDay(day)
            }
            champ?.deleteFromRealm()
        }

        fun removeDay(id: Int, champ: Int) {
            val day = findDay(id, champ)
            day?.let { removeDay(it) }
        }

        fun removeDay(day: DayData) {
            day.matches.deleteAllFromRealm()
            day.deleteFromRealm()
        }

        fun removeMatch(id: Int, day: Int, champ: Int) {
            val match = findMatch(id, day, champ)
            match?.deleteFromRealm()
        }

        fun removePlayer(id: Int) {
            val player = findPlayer(id)
            player?.deleteFromRealm()
        }

        fun removeEvent(id: Int, match: Int, day: Int, champ: Int) {
            val event = findEvent(id, match, day, champ)
            event?.deleteFromRealm()
        }

        fun removeMatchPlayer(id: Int, match: Int, day: Int, champ: Int) {
            val matchPlayer = findMatchPlayer(id, match, day, champ)
            matchPlayer?.deleteFromRealm()
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

        fun getTableUpdateTime(tableName: String): String? {
            val realm = Realm.getDefaultInstance()
            val date = realm.where(TableUpdateTimersData::class.java)
                .equalTo("tableName", tableName, Case.INSENSITIVE)
                .findFirst()

            date?.let {
                return dateTimeFormatter.format(it.updateTime)
            } ?: run {
                return null
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

    } // fin companion Object


    fun getAllChampData(): List<ChampData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<ChampData>().findAll()
    }

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