package be.technifutur.devmob9.sdsftryone.webservice

import be.technifutur.devmob9.sdsftryone.dao.DbManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AllTableDataReader: DataReader {
    override fun accept(nextReaders: LinkedList<DataReader>) {

        val updateTime = DbManager.getTableUpdateTime()

        WebService.interfaceInstance.readAll(WebService.uuid, updateTime).enqueue(object: Callback<AllTable> {
            override fun onFailure(call: Call<AllTable>, t: Throwable) {
                WebService.failure(t)
            }

            override fun onResponse(call: Call<AllTable>, response: Response<AllTable>) {
                response.body()?.let {
                    // traite les données
                    WebService.updateClub(it.club)
                    WebService.updateChamp(it.champ)
                    WebService.updateDay(it.day)
                    WebService.updateMatch(it.match)
                    WebService.updatePlayer(it.player)
                    WebService.updateEvent(it.event)
                    WebService.updateMatchPlayer(it.matchPlayer)
                }
                callNext(nextReaders)
            }
        })
    }
}