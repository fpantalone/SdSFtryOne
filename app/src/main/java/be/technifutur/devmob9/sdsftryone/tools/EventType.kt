package be.technifutur.devmob9.sdsftryone.tools

enum class EventType(val jsonType: String) {
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
    CHRONO("chrono")
}
