package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import android.util.Log
import be.technifutur.devmob9.sdsftryone.model.*
import io.realm.Case
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DbManager {

    companion object {
        lateinit var instance: DbManager private set

        fun init(context: Context) {
            instance = DbManager()
            instance.context = context
            Realm.init(context)
        }

        fun sharedInstance(): DbManager {
            return instance
        }
    }

    private lateinit var context: Context
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE)


    // remplir la DB

    fun fillDataBase() {

        if (getTableUpdateTimersData().isNotEmpty()) {
            return
        }

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // remplir db
    }

    fun getAllChmapData(): List<ChampData> {
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

    fun getMatchData(): List<MatchData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchData>().findAll()
    }

    fun getTableUpdateTimersData(): List<TableUpdateTimersData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<TableUpdateTimersData>().findAll()
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

    fun addClub(code: String, shortName: String, fullName: String, logo: String?): ClubData? {
        val realm = Realm.getDefaultInstance()
        try {
            val club = realm.createObject(ClubData::class.java, code)
            club.shortName = shortName
            club.fullName = fullName
            club.logo = logo
            return club
        } catch (e: RealmException) {
            Log.d("DBMANAGER", e.message ?: "addClub")
        }
        return null
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
        try {
            val champ = realm.createObject(ChampData::class.java, id)
            champ.name = name
            champ.numDay = numDay
            champ.season = season
            champ.matchConfig = matchConfig
            champ.teams.addAll(teams)
            champ.genForfeit.addAll(genForfeit)

        } catch (e: RealmException) {
            Log.d("DBMANAGER", e.message ?: "addChamp")
        }
        return null
    }

    fun addDay(
        id: Int, champ: Int, name: String, date: Date,
        comment: String): DayData? {
        val realm = Realm.getDefaultInstance()

        try {
            val day = realm.createObject(DayData::class.java, id)
            day.name = name
            day.date = date
            day.comment = comment

            val champData = findChamp(champ)
            champData?.days?.add(day)
            return day

        } catch (e: RealmException) {
            Log.d("DBMANAGER", e.message ?: "addDay")
        }
        return null
    }

    fun addMatch (id: Int, day: Int, champ: Int, date: Date?, hour: String, homeTeam: String,
                  awayTeam: String, homeScore: Int?, awayScore: Int?, comment: String): MatchData? {

        val realm = Realm.getDefaultInstance()

        try {
            val match = realm.createObject(MatchData::class.java, id)

            match.date = date
            match.hour = hour
            match.homeTeam = homeTeam
            match.awayTeam = awayTeam
            match.homeResult = homeScore
            match.awayResult = awayScore
            match.comment = comment

            val matchDay = findDay(day, champ)
            matchDay?.matches?.add(match)

            return match
        }
        catch (e: RealmException){
            Log.d ("DBMANAGER", e.message ?: "addMatch")
        }
        return null
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

    fun removeMatch(id: Int,  day: Int, champ: Int) {
        val match = findMatch(id, day, champ)
        match?.deleteFromRealm()
    }

    fun getTableUpdateTime(tableName: String): String? {
        val realm = Realm.getDefaultInstance()
        val date = realm.where(TableUpdateTimersData::class.java)
            .equalTo("tableName", tableName, Case.INSENSITIVE)
            .findFirst()

        date?.let {
            return dateFormatter.format(it.updateTime)
        } ?: run {
            return null
        }
    }
}