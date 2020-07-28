package be.technifutur.devmob9.sdsftryone.tools

enum class LockStatus (jsonString: String) {
    OPEN("no"),
    CLOSED("yes"),
    OWNED("own")
}