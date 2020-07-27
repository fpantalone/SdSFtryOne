package be.technifutur.devmob9.sdsftryone

import android.app.Application
import be.technifutur.devmob9.sdsftryone.dao.DbManager
import be.technifutur.devmob9.sdsftryone.webservice.WebService

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DbManager.init(this)
        WebService.init(this)
    }

}