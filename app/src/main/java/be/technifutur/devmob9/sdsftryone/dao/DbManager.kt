package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import android.util.Log
import be.technifutur.devmob9.sdsftryone.model.*
import io.realm.Case
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.kotlin.where
import java.text.SimpleDateFormat

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
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


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

    fun addClub(code: String, shortName: String, fullName: String, logo: String?): ClubData? {
        val realm = Realm.getDefaultInstance()
        try {
            val club = realm.createObject(ClubData::class.java, code)
            club.shortName = shortName
            club.fullName = fullName
            club.logo = logo
            return club
        }
        catch (e: RealmException) {
            Log.d("DBMANAGER", e.message?:"")
        }
        return null
    }

    fun removeClub(code: String) {
        val club = findClub(code)
        club?.deleteFromRealm()
    }

    fun getTableUpdateTime(tableName: String): String? {
        val realm = Realm.getDefaultInstance()
        val date = realm.where(TableUpdateTimersData::class.java)
            .equalTo("tableName", tableName, Case.INSENSITIVE)
            .findFirst()

//        if (date == null) {
//            return null
//        }
//        else {
//            return dateFormatter.format(date)
//        }
        date?.let {
            return dateFormatter.format(it.updateTime)
        } ?: run {
            return null
        }

    }

}