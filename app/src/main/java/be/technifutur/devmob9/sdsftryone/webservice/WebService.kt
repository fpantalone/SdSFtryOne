package be.technifutur.devmob9.sdsftryone.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("query.php?cmd=tut")
    fun readUpdateTime(@Query("uuid") uuid: String): Call<TableUpdateTimes>

    @GET("query.php?cmd=club")
    fun readClub(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Club>>

    @GET("query.php?cmd=champ")
    fun readChamp(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Champ>>

    @GET("query.php?cmd=day")
    fun readDay(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Day>>

    @GET("query.php?cmd=match")
    fun readMatch(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Match>>

    @GET("query.php?cmd=genfft")
    fun readGeneralForfeit(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<GeneralForfeit>>
}