package be.technifutur.devmob9.sdsftryone.tools

import android.os.AsyncTask
import be.technifutur.devmob9.sdsftryone.webservice.WebService

class LodingAsynchTask(val listener: AsynchTaskListener): AsyncTask<Int, Void,AllTableResult> () {
    override fun doInBackground(vararg params: Int?): AllTableResult {

        return WebService.updateDataBase()
    }
}