package be.technifutur.devmob9.sdsftryone.webservice

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.preference.PreferenceManager
import be.technifutur.devmob9.sdsftryone.BuildConfig
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WebService() {

    companion object {
        private lateinit var context: Context
        private lateinit var retrofit: Retrofit
        internal lateinit var interfaceInstance: WebServiceInterface
        lateinit var uuid: String
            private set
        var dbUpdateTime: Date? = null
            private set
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        private val dateTimeFormater = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.ROOT)
        private var currentCall: Disposable? = null

        fun init (context: Context) {
            WebService.context = context

            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            interfaceInstance = retrofit.create(WebServiceInterface::class.java)

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val spUuid = preferences.getString("app_uuid", "")
            if (spUuid?.isEmpty() != false) {
                val editor = preferences.edit()
                uuid = UUID.randomUUID().toString().toUpperCase()
                editor.putString("app_uuid", uuid)
                editor.apply()
            } else {
                uuid = spUuid
            }
            val spDbUpdateTime = preferences.getString("app_db_update_time", "")
            if (spDbUpdateTime?.isNotEmpty() == true) {
                dbUpdateTime = dateTimeFormater.parse(spDbUpdateTime)
            }
        }

        fun isOnline(): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
            }
            return false
        }

        fun updateDbSyncTime () {
            dbUpdateTime = Date()
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("app_db_update_time", dateTimeFormater.format(dbUpdateTime!!))
            editor.apply()
        }

        fun clearDbSyncTime () {
            dbUpdateTime = null
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("app_db_update_time", null)
            editor.apply()
        }

        private fun <T>dispose (consumer: Consumer<T>): Consumer<T> {
            return Consumer { t: T ->
                consumer.accept(t)
                currentCall?.dispose()
                currentCall = null
            }
        }

        fun getAllData (onSuccess: Consumer<AllTable>, onError: Consumer<Throwable>) {
            // si on a du réseau
            if (isOnline()) {
                currentCall?.dispose()
                currentCall = interfaceInstance.readAll(uuid, dateTimeFormater.format(dbUpdateTime!!))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dispose(onSuccess), dispose(onError))
            }
            // si on a pas de réseau
            else {
                // on a pas de réseau, on en informe l'utilisateur
                onError.accept(IllegalStateException("No Internet connection"))
            }
        }

//        fun updateMatch(matchList: List<Match>) {
//            for (match in matchList) {
//                when (match.action) {
//                    'A' -> {
//                        var date: Date? = null
//                        match.date?.let {
//                            date = dateFormatter.parse(it)
//                        }
//
//                        DbManager.addMatch(
//                            match.id, match.day, match.champ, date,
//                            match.hour, match.eq1, match.eq2, match.re1, match.re2,
//                            match.comment ?: "", match.locked
//                        )
//                    }
//                    'U' -> {
//                        val dbMatch = DbManager.findMatch(match.id, match.day, match.champ)
//                        dbMatch?.hour = match.hour
//                        dbMatch?.homeTeam = match.eq1
//                        dbMatch?.awayTeam = match.eq2
//                        dbMatch?.homeScore = match.re1
//                        dbMatch?.awayScore = match.re2
//                        dbMatch?.comment = match.comment ?: ""
//                        dbMatch?.locked = match.locked
//                    }
//                    'R' -> {
//                        DbManager.removeMatch(match.id, match.day, match.champ)
//                    }
//                }
//            }
//        }

//        fun updateDay(dayList: List<Day>) {
//
//            for (day in dayList) {
//                when (day.action) {
//                    'A' -> {
//                        dateFormatter.parse(day.date)?.let { date ->
//                            DbManager
//                                .addDay(
//                                    day.id, day.champ, day.name ?: "", date,
//                                    day.comment ?: ""
//                                )
//                        }
//                    }
//
//                    'U' -> {
//                        val dbDay = DbManager.findDay(day.id, day.champ)
//                        dbDay?.name = day.name ?: ""
//                        dbDay?.date = dateFormatter.parse(day.date) ?: Date()
//                        dbDay?.comment = day.comment ?: ""
//                    }
//                    'R' -> {
//                        DbManager.removeDay(day.id, day.champ)
//                    }
//                }
//            }
//        }

//        fun updateChamp(champList: List<Champ>) {
//            for (champ in champList) {
//                when (champ.action) {
//                    'A' -> {
//                        val teams = ArrayList<ChampTeamData>()
//
//                        champ.team?.let {
//                            val champTeam = DbManager.addChampTeam(it, "#00016")
//                            if (champTeam != null) {
//                                teams.add(champTeam)
//                            }
//                        }
//
//                        champ.teams?.let {
//                            for (team in it) {
//                                val champTeam =
//                                    DbManager.addChampTeam(team.team, team.code)
//                                if (champTeam != null) {
//                                    teams.add(champTeam)
//                                }
//                            }
//                        }
//
//                        DbManager.addChamp(
//                            champ.id, champ.name, champ.numDay,
//                            champ.season, champ.matchConfig, champ.genForfeit, teams
//                        )
//                    }
//                    'U' -> {
//
//                        val teams = ArrayList<ChampTeamData>()
//
//                        champ.team?.let {
//                            val champTeam = DbManager.addChampTeam(it, "#00016")
//                            if (champTeam != null) {
//                                teams.add(champTeam)
//                            }
//                        }
//
//                        champ.teams?.let {
//                            for (team in it) {
//                                val champTeam =
//                                    DbManager.addChampTeam(team.team, team.code)
//                                if (champTeam != null) {
//                                    teams.add(champTeam)
//                                }
//                            }
//                        }
//
//                        val dbChamp = DbManager.findChamp(champ.id)
//
//                        dbChamp?.name = champ.name
//                        dbChamp?.numDay = champ.numDay
//                        dbChamp?.season = champ.season
//                        dbChamp?.genForfeit?.addAll(champ.genForfeit)
//                        dbChamp?.teams?.addAll(teams)
//                    }
//                    'R' -> {
//                        DbManager.removeChamp(champ.id)
//                    }
//                }
//
//            }
//        }

//        fun updateClub(clubList: List<Club>) {
//            for (club in clubList) {
//                when (club.action) {
//                    'A' -> {
//                        DbManager.addClub(club.code, club.short, club.full, club.logo)
//
//                    }
//                    'U' -> {
//                        val dbClub = DbManager.findClub(club.code)
//                        dbClub?.shortName = club.short
//                        dbClub?.fullName = club.full
//                        dbClub?.logo = club.logo
//                    }
//                    'R' -> {
//                        DbManager.removeClub(club.code)
//                    }
//                }
//            }
//        }

//        fun updatePlayer(playerList: List<Player>?) {
//            if (null == playerList) {
//                return
//            }
//            for (player in playerList) {
//                when (player.action) {
//                    'A' -> {
//                        DbManager.addPlayer(
//                            player.id,
//                            player.firstName,
//                            player.lastName,
//                            player.number,
//                            player.team
//                        )
//                    }
//                    'U' -> {
//                        val dbPlayer = DbManager.findPlayer(player.id)
//                        dbPlayer?.firstName = player.firstName
//                        dbPlayer?.firstName = player.firstName
//                        dbPlayer?.number = player.number
//                        dbPlayer?.team?.addAll(player.team)
//                    }
//                    'R' -> {
//                        DbManager.removePlayer(player.id)
//                    }
//                }
//            }
//        }

//        fun updateEvent(eventList: List<Event>?) {
//            if (null == eventList) {
//                return
//            }
//            for (event in eventList) {
//                when (event.action) {
//                    'A' -> {
//                        DbManager.addEvent(
//                            event.id, event.match, event.day, event.champ,
//                            event.time, event.team, event.type, event.param
//                        )
//                    }
//                    'U' -> {
//                        val dbEvent =
//                            DbManager.findEvent(event.id, event.match, event.day, event.champ)
//                        dbEvent?.time = event.time
//                        dbEvent?.team = event.team
//                        dbEvent?.type = event.type
//                        dbEvent?.param = event.param
//                    }
//                    'R' -> {
//                        DbManager.removeEvent(event.id, event.match, event.day, event.champ)
//                    }
//                }
//            }
//        }

//        fun updateMatchPlayer(matchPlayerList: List<MatchPlayer>?) {
//            if (null == matchPlayerList) {
//                return
//            }
//            for (matchPlayer in matchPlayerList) {
//                when (matchPlayer.action) {
//                    'A' -> {
//                        DbManager.addMatchPlayer(
//                            matchPlayer.id,
//                            matchPlayer.match,
//                            matchPlayer.day,
//                            matchPlayer.champ,
//                            matchPlayer.player,
//                            matchPlayer.name,
//                            matchPlayer.number,
//                            matchPlayer.status
//                        )
//                    }
//                    'U' -> {
//                        val dbMatchPlayer = DbManager.findMatchPlayer(
//                            matchPlayer.id,
//                            matchPlayer.match,
//                            matchPlayer.day,
//                            matchPlayer.champ
//                        )
//                        // on ne chamge pas le player
//                        dbMatchPlayer?.name = matchPlayer.name
//                        dbMatchPlayer?.number = matchPlayer.number
//                        dbMatchPlayer?.status = matchPlayer.status
//                    }
//                    'R' -> {
//                        DbManager.removeMatchPlayer(
//                            matchPlayer.id,
//                            matchPlayer.match,
//                            matchPlayer.day,
//                            matchPlayer.champ
//                        )
//                    }
//                }
//            }
//        }
    }
}