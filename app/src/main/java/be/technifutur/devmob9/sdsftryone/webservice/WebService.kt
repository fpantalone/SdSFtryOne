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
                editor.putBoolean("team_a_pref",true)
                editor.putBoolean("team_b_pref",true)
                editor.putBoolean("team_b_pref",true)
                editor.putBoolean("team_c_pref",true)
                editor.putBoolean("team_u16_pref",true)
                editor.putBoolean("team_u14_pref",true)
                editor.putBoolean("team_u13_pref",true)
                editor.putBoolean("team_u12_pref",true)
                editor.putBoolean("team_u11_pref",true)
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
            val since = if (dbUpdateTime != null) {
                dateTimeFormater.format(dbUpdateTime!!)
            }
            else {
                null
            }
            editor.putString("app_db_update_time", since)
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
                val since = if (dbUpdateTime != null) {
                    dateTimeFormater.format(dbUpdateTime!!)
                }
                else {
                    null
                }
                currentCall = interfaceInstance.readAll(uuid, since)
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
    }
}