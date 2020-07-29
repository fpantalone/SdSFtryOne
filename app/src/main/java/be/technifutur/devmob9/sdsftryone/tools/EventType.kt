package be.technifutur.devmob9.sdsftryone.tools

enum class EventType(val jsonString: String): StringDataConverter {
    GOAL("goal"),
    FOUL("foul"),
    OFFSIDE("offside"),
    CORNER("corner"),
    ONTARGET("ontarget"),
    OFFTARGET("offtarget"),
    SUBSTITUTION("substitution("),
    CARD("card"),
    BALLGAIN("ballgain"),
    PENALTY("penalty"),
    CHRONO("chrono");

    companion object: StringDataCreator<EventType> {
        override fun createFrom(string: String): EventType? {
            return values().first {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}
