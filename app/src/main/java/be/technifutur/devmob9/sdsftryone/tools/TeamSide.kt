package be.technifutur.devmob9.sdsftryone.tools

enum class TeamSide (val jsonString: String): StringDataConverter {
    HOME("home"),
    AWAY("away");

    val opponentSide: TeamSide
    get() = if (this == HOME) { AWAY} else HOME

    companion object: StringDataCreator<TeamSide> {
        override fun createFrom(string: String): TeamSide? {
            return values().firstOrNull {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}
