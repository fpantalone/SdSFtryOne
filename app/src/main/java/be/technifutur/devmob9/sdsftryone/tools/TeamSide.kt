package be.technifutur.devmob9.sdsftryone.tools

enum class TeamSide (val side: String) {
    HOME("home"),
    AWAY("away");

    val opponentSide: TeamSide
    get() = if (this == HOME) { AWAY} else HOME

}
