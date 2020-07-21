package be.technifutur.devmob9.sdsftryone.webservice

import retrofit2.Call
import retrofit2.http.*

interface WebServiceInterface {

    @GET("request.php?cmd=get&table=tut")
    fun readUpdateTime(@Query("uuid") uuid: String): Call<TableUpdateTimes>

    @GET("request.php?cmd=get&table=club")
    fun readClub(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Club>>

    @GET("request.php?cmd=get&table=champ")
    fun readChamp(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Champ>>

    @GET("request.php?cmd=get&table=day")
    fun readDay(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Day>>

    @GET("request.php?cmd=get&table=match")
    fun readMatch(@Query("uuid") uuid: String, @Query("since") since: String?): Call<List<Match>>

    @GET("request.php?cmd=get&table=all")
    fun readAll(@Query("uuid") uuid: String, @Query("since") since: String?): Call<AllTable>

    @POST("request.php?cmd=do")
    fun sendMatchData(@Body modifications: List<Modification>, @Field ("uuid") uuid: String): Call<Int>
}