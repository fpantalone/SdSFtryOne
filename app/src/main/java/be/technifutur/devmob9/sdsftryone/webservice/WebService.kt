package be.technifutur.devmob9.sdsftryone.webservice

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import androidx.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import be.technifutur.devmob9.sdsftryone.BuildConfig
import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.fragment.SplashFragment
import be.technifutur.devmob9.sdsftryone.model.ChampTeamData
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

class WebService() {

    companion object {
        private lateinit var context: Context

        private lateinit var retrofit: Retrofit
        lateinit var interfaceInstance: WebServiceInterface private set
        lateinit var uuid: String private set
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun init(context: Context) {
            WebService.context = context

            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            interfaceInstance = retrofit.create(WebServiceInterface::class.java)

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val spUuid = preferences.getString("app_uuid", "")
            if (spUuid?.isEmpty() ?: true) {
                val editor = preferences.edit()
                uuid = UUID.randomUUID().toString().toUpperCase()
                editor.putString("app_uuid", uuid)
                editor.apply()
            } else {
                uuid = spUuid!!
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
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
            }
            return false
        }

        fun displayError(navController: NavController? = null) {

            Toast.makeText(context, context.getString(R.string.loading_error), Toast.LENGTH_SHORT)
                .show()

            navController?.let {
                Handler().postDelayed({
                    it.navigate(R.id.action_splashFragment_to_homeFragment)
                }, SplashFragment.SPLASH_SCREEN_DURATION.toLong())
            }
        }

        private var navController: NavController? = null

        fun updateDataBase(navController: NavController? = null) {
            this.navController = navController
            // si on a du réseau
            if (isOnline()) {
                // liste de DataReader
                val readers = LinkedList<DataReader>()
                readers.add(AllTableDataReader())
                readers.add(object : DataReader {
                    override fun accept(t: LinkedList<DataReader>) {
                        DbManager.endUpdate()
                        WebService.navController?.let {
                            // passer à l'écran d'accueil
                            it.navigate(R.id.action_splashFragment_to_homeFragment)
                        }
                    }
                })
//                readers.add  (DataReader { _: LinkedList<DataReader> ->
//                    WebService.navController?.let {
//                        // passer à l'écran d'accueil
//                        it.navigate(R.id.action_splashFragment_to_homeFragment)
//                    }
//                    DbManager.sharedInstance().endUpdate()
//                    return@DataReader
//                })
                DbManager.startUpdate()
                DataReader.start(readers)
            }
            // si on a pas de réseau
            else {
                // on a pas de réseau on informe l'utilisateur
                Toast.makeText(context, "il n'y a pas de réseau", Toast.LENGTH_SHORT).show()
            }
        }

        fun failure(t: Throwable) {
            displayError(navController)
            Log.d("WEBSERVICE", t.message ?: "")
        }

        fun updateMatch(matchList: List<Match>) {
            for (match in matchList) {
                when (match.action) {
                    'A' -> {
                        var date: Date? = null
                        match.date?.let {
                            date = dateFormatter.parse(it)
                        }

                        DbManager.addMatch(
                            match.id, match.day, match.champ, date,
                            match.hour, match.eq1, match.eq2, match.re1, match.re2,
                            match.comment ?: "", match.locked
                        )
                    }
                    'U' -> {
                        val dbMatch = DbManager.findMatch(match.id,match.day,match.champ)
                        dbMatch?.hour = match.hour
                        dbMatch?.homeTeam = match.eq1
                        dbMatch?.awayTeam = match.eq2
                        dbMatch?.homeResult = match.re1
                        dbMatch?.awayResult = match.re2
                        dbMatch?.comment = match.comment ?:""
                        dbMatch?.locked = match.locked
                    }
                    'R' -> {
                        DbManager.removeMatch(match.id, match.day, match.champ)
                    }
                }
            }
        }

        fun updateDay(dayList: List<Day>) {

            for (day in dayList) {
                when (day.action) {
                    'A' -> {
                        dateFormatter.parse(day.date)?.let { date ->
                            DbManager
                                .addDay(
                                    day.id, day.champ, day.name ?: "", date,
                                    day.comment ?: ""
                                )
                        }
                    }

                    'U' -> {
                        val dbDay = DbManager.findDay(day.id, day.champ)
                        dbDay?.name = day.name ?: ""
                        dbDay?.date = dateFormatter.parse(day.date) ?: Date()
                        dbDay?.comment = day.comment ?: ""
                    }
                    'R' -> {
                        DbManager.removeDay(day.id, day.champ)
                    }
                }
            }
        }

        fun updateChamp(champList: List<Champ>) {
            for (champ in champList) {
                when (champ.action) {
                    'A' -> {
                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = DbManager.addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    DbManager.addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        DbManager.addChamp(
                            champ.id, champ.name, champ.numDay,
                            champ.season, champ.matchConfig, champ.genForfeit, teams
                        )
                    }
                    'U' -> {

                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = DbManager.addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    DbManager.addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        val dbChamp = DbManager.findChamp(champ.id)

                        dbChamp?.name = champ.name
                        dbChamp?.numDay = champ.numDay
                        dbChamp?.season = champ.season
                        dbChamp?.genForfeit?.addAll(champ.genForfeit)
                        dbChamp?.teams?.addAll(teams)
                    }
                    'R' -> {
                        DbManager.removeChamp(champ.id)
                    }
                }

            }
        }

        fun updateClub(clubList: List<Club>) {
            for (club in clubList) {
                when (club.action) {
                    'A' -> {
                        DbManager.addClub(club.code, club.short, club.full, club.logo)

                    }
                    'U' -> {
                        val dbClub = DbManager.findClub(club.code)
                        dbClub?.shortName = club.short
                        dbClub?.fullName = club.full
                        dbClub?.logo = club.logo
                    }
                    'R' -> {
                        DbManager.removeClub(club.code)
                    }
                }
            }
        }

        fun updatePlayer(playerList: List<Player>?) {
            if (null == playerList) {
                return
            }
            // TODO("Not yet implemented")
        }

        fun updateEvent(eventList: List<Event>?) {
            if (null == eventList) {
                return
            }
            // TODO("Not yet implemented")
        }

        fun updateMatchPlayer(matchPlayerList: List<MatchPlayer>?) {
            if (null == matchPlayerList) {
                return
            }
            // TODO("Not yet implemented")
        }
    }
}