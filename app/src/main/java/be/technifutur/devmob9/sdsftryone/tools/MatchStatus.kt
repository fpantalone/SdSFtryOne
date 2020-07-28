package be.technifutur.devmob9.sdsftryone.tools

enum class MatchStatus (val jsonString: String) {

    POSTPONE("rem!"),
    FORFEIT("fft!"),
    LIVE("live!"),
    STOPPED("stop!"),
    NONE("")
}
