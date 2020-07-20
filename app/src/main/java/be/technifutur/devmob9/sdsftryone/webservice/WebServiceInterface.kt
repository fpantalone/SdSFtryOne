package be.technifutur.devmob9.sdsftryone.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WebServiceInterface {

    @POST("request.php?cmd=tut")
    fun readUpdateTime(@Query("uuid") uuid: String): Call<TableUpdateTimes>

    @POST("request.php?cmd=club")
    fun readClub(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Club>>

    @POST("request.php?cmd=champ")
    fun readChamp(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Champ>>

    @POST("request.php?cmd=day")
    fun readDay(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Day>>

    @POST("request.php?cmd=match")
    fun readMatch(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Match>>

}