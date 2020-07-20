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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WebService() {

    companion object {
        private lateinit var context: Context

        private lateinit var retrofit: Retrofit
        private lateinit var webService: WebServiceInterface
        private lateinit var uuid: String

        fun init(context: Context) {
            WebService.context = context

            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            webService = retrofit.create(WebServiceInterface::class.java)

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

        fun updateDataBase(navController: NavController? = null) {

            if (WebService.isOnline()) {

                // on a du réseau on peut bosser

                webService.readUpdateTime(uuid).enqueue(object : Callback<TableUpdateTimes> {
                    override fun onFailure(call: Call<TableUpdateTimes>, t: Throwable) {
                        displayError(navController)
                    }

                    override fun onResponse(
                        call: Call<TableUpdateTimes>,
                        response: Response<TableUpdateTimes>
                    ) {
                        response.body()?.let {
                            // fonction qui analyse les dates de la DB et celle qu'on a reçu

                            val clubUpdateTime =
                                DbManager.sharedInstance().getTableUpdateTime("club")

                            if ((clubUpdateTime ?: "") < it.club) {
                                webService.readClub(uuid, clubUpdateTime)
                                    .enqueue(object : Callback<List<Club>> {

                                        override fun onFailure(
                                            call: Call<List<Club>>,
                                            t: Throwable
                                        ) {
                                            displayError(navController)
                                        }

                                        override fun onResponse(
                                            call: Call<List<Club>>,
                                            response: Response<List<Club>>
                                        ) {
                                            response.body()?.let {
                                                updateClub(it)
                                            }
                                            // le championnat

                                            val champUpdateTime =
                                                DbManager.sharedInstance()
                                                    .getTableUpdateTime("champ")

                                            if ((champUpdateTime ?: "") < it.champ) {
                                                webService.readChamp(uuid, champUpdateTime)
                                                    .enqueue(object : Callback<List<Champ>> {
                                                        override fun onFailure(
                                                            call: Call<List<Champ>>,
                                                            t: Throwable
                                                        ) {
                                                            displayError(navController)
                                                        }

                                                        override fun onResponse(
                                                            call: Call<List<Champ>>,
                                                            response: Response<List<Champ>>
                                                        ) {
                                                            response.body()?.let {
                                                                updateChamp(it)
                                                            }
                                                            // Day
                                                            val dayUpdateTime =
                                                                DbManager.sharedInstance()
                                                                    .getTableUpdateTime("day")

                                                            if ((dayUpdateTime ?: "") < it.day) {
                                                                webService.readDay(
                                                                    uuid,
                                                                    dayUpdateTime
                                                                )
                                                                    .enqueue(object :
                                                                        Callback<List<Day>> {
                                                                        override fun onFailure(
                                                                            call: Call<List<Day>>,
                                                                            t: Throwable
                                                                        ) {
                                                                            displayError(
                                                                                navController
                                                                            )
                                                                        }

                                                                        override fun onResponse(
                                                                            call: Call<List<Day>>,
                                                                            response: Response<List<Day>>
                                                                        ) {
                                                                            response.body()?.let {
                                                                                updateDay(it)
                                                                            }
                                                                            // Match
                                                                            val matchUpdateTime =
                                                                                DbManager.sharedInstance()
                                                                                    .getTableUpdateTime(
                                                                                        "match"
                                                                                    )

                                                                            if ((matchUpdateTime
                                                                                    ?: "") < it.match
                                                                            ) {
                                                                                webService.readMatch(
                                                                                    uuid,
                                                                                    matchUpdateTime
                                                                                )
                                                                                    .enqueue(object :
                                                                                        Callback<List<Match>> {
                                                                                        override fun onFailure(
                                                                                            call: Call<List<Match>>,
                                                                                            t: Throwable
                                                                                        ) {
                                                                                            displayError(
                                                                                                navController
                                                                                            )
                                                                                        }

                                                                                        override fun onResponse(
                                                                                            call: Call<List<Match>>,
                                                                                            response: Response<List<Match>>
                                                                                        ) {
                                                                                            response.body()
                                                                                                ?.let {
                                                                                                    updateMatch(
                                                                                                        it
                                                                                                    )
                                                                                                }
                                                                                        }
                                                                                    })
                                                                            }
                                                                        }
                                                                    })
                                                            }
                                                        }
                                                    })
                                            }

                                        }
                                    })
                            }

                        }
                    }
                })

            } else {
                // on a pas de réseau on informe l'utilisateur
                Toast.makeText(context, "il n'y a pas de réseau", Toast.LENGTH_SHORT).show()
            }
        }

        private fun updateMatch(matchList: List<Match>) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            for (match in matchList) {
                when (match.action) {
                    'A' -> {
                      //  DbManager.sharedInstance().addMatch(match.id,match.day, match.)
                    }
                    'R' -> {

                    }
                    'U' -> {

                    }
                }
            }

            realm.commitTransaction()
        }

        private fun updateDay(dayList: List<Day>) {

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE)

            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            for (day in dayList) {
                when (day.action) {
                    'A' -> {

                        val date = dateFormatter.parse(day.date)

                        DbManager.sharedInstance()
                            .addDay(day.id, day.champ,day.name ?: "",date ?: Date(),
                                day.comment ?: "")
                    }

                    'U' -> {
                        val dbDay = DbManager.sharedInstance().findDay(day.id, day.champ)
                        dbDay?.name = day.name ?: ""
                        dbDay?.date = dateFormatter.parse(day.date) ?: Date()
                        dbDay?.comment = day.comment ?: ""
                    }
                    'R' -> {
                        DbManager.sharedInstance().removeDay(day.id, day.champ)
                    }
                }
            }

            realm.commitTransaction()
        }

        private fun updateChamp(champList: List<Champ>) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            for (champ in champList) {
                when (champ.action) {
                    'A' -> {
                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = DbManager.sharedInstance().addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    DbManager.sharedInstance().addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        DbManager.sharedInstance().addChamp(
                            champ.id, champ.name, champ.numDay,
                            champ.season, champ.matchConfig, champ.genForfeit, teams
                        )
                    }
                    'U' -> {

                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = DbManager.sharedInstance().addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    DbManager.sharedInstance().addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        val dbChamp = DbManager.sharedInstance().findChamp(champ.id)

                        dbChamp?.name = champ.name
                        dbChamp?.numDay = champ.numDay
                        dbChamp?.season = champ.season
                        dbChamp?.genForfeit?.addAll(champ.genForfeit)
                        dbChamp?.teams?.addAll(teams)
                    }
                    'R' -> {
                        DbManager.sharedInstance().removeChamp(champ.id)
                    }
                }

            }
            realm.commitTransaction()
        }

        private fun updateClub(clubList: List<Club>) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            for (club in clubList) {
                when (club.action) {
                    'A' -> {
                        DbManager.sharedInstance()
                            .addClub(club.code, club.short, club.full, club.logo)

                    }
                    'U' -> {
                        val dbClub = DbManager.sharedInstance().findClub(club.code)
                        dbClub?.shortName = club.short
                        dbClub?.fullName = club.full
                        dbClub?.logo = club.logo
                    }
                    'R' -> {
                        DbManager.sharedInstance().removeClub(club.code)
                    }
                }
            }
            realm.commitTransaction()
        }
    }
}