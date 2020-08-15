package be.technifutur.devmob9.sdsftryone.webservice

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface WebServiceInterface {
    @GET("request.php?cmd=get&table=all")
    fun readAll(@Query("uuid") uuid: String, @Query("since") since: String?): Single<AllTable>

    @POST("request.php?cmd=do")
    fun sendMatchData(
        @Body modifications: List<Modification>,
        @Field("uuid") uuid: String
    ): Single<Int>

    @POST ("request.php?cmd=lock")
    fun lockMatch (@Field("uuid") uuid: String, @Field("champ") champ: Int,
                   @Field("day") day: Int, @Field("match") match: Int): Single<Boolean>

    @POST ("request.php?cmd=unlock")
    fun unlockMatch (@Field("uuid") uuid: String, @Field("champ") champ: Int,
                   @Field("day") day: Int, @Field("match") match: Int): Single<Boolean>
}