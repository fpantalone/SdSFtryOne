package be.technifutur.devmob9.sdsftryone.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {

    @GET("request.php?cmd=tut")
    fun readUpdateTime(@Query("uuid") uuid: String): Call<TableUpdateTimes>

    @GET("request.php?cmd=club")
    fun readClub(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Club>>

    @GET("request.php?cmd=champ")
    fun readChamp(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Champ>>

    @GET("request.php?cmd=day")
    fun readDay(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Day>>

    @GET("request.php?cmd=match")
    fun readMatch(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Match>>

}