package be.technifutur.devmob9.sdsftryone.tools

enum class MatchStatus (val jsonString: String): StringDataConverter {

    POSTPONE("rem!"),
    FORFEIT("fft!"),
    LIVE("live!"),
    STOPPED("stop!"),
    NONE("");

    companion object: StringDataCreator<MatchStatus> {
        override fun createFrom(string: String): MatchStatus? {
            return values().first {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}
