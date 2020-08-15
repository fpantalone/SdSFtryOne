package be.technifutur.devmob9.sdsftryone.webservice

import com.google.gson.annotations.SerializedName

class AllTable(
    val club: List<Club> = listOf(),
    val champ: List<Champ> = listOf(),
    val day: List<Day> = listOf(),
    val match: List<Match> = listOf(),
    val player: List<Player> = listOf(),
    val event: List<Event> = listOf(),
    @SerializedName("match_player")
    val matchPlayer: List<MatchPlayer> = listOf()
) {
}