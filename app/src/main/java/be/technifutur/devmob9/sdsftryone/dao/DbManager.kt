package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import be.technifutur.devmob9.sdsftryone.model.*
import io.realm.Realm
import io.realm.kotlin.where

class DbManager {

    private var instance: DbManager? = null
    private lateinit var context: Context

    fun sharedInstance(): DbManager? {
        return instance
    }

    fun init(context: Context) {
        instance = DbManager()
        instance!!.context = context
        Realm.init(context)
    }

    // remplir la DB

    fun fillDataBase (){

        if (getTableUpdateTimersData().isNotEmpty()){
            return
        }

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

    }

    fun getAllChmapData (): List<ChampData>{
        val realm = Realm.getDefaultInstance()
        return realm.where<ChampData>().findAll()
    }

    fun getAllClubData (): List<ClubData>{
        val realm = Realm.getDefaultInstance()
        return realm.where<ClubData>().findAll()
    }

    fun getAllDayData (): List<DayData>{
        val realm = Realm.getDefaultInstance()
        return realm.where<DayData>().findAll()
    }


    fun getMatchData (): List<MatchData>{
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchData>().findAll()
    }
    fun getTableUpdateTimersData (): List<TableUpdateTimersData>{
        val realm = Realm.getDefaultInstance()
        return realm.where<TableUpdateTimersData>().findAll()
    }

}