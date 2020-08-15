package be.technifutur.devmob9.sdsftryone.tools

enum class EventType(val jsonString: String): StringDataConverter {
    GOAL("goal"),
    FOUL("foul"),
    OFFSIDE("offside"),
    CORNER("corner"),
    ONTARGET("onTarget"),
    OFFTARGET("offTarget"),
    SUBSTITUTION("substitution"),
    CARD("card"),
    BALLGAIN("ballGain"),
    PENALTY("penalty"),
    CHRONO("chrono");

    companion object: StringDataCreator<EventType> {
        override fun createFrom(string: String): EventType? {
            return values().firstOrNull {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}
