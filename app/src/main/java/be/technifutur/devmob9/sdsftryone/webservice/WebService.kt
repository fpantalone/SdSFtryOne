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
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

        private fun updateClub(clubList: List<Club>) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()

            for (club in clubList) {
                when (club.action) {
                    'A' -> {
                        DbManager.sharedInstance().addClub(club.code, club.short, club.full, club.logo)

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