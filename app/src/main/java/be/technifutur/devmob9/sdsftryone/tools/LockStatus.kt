package be.technifutur.devmob9.sdsftryone.tools

enum class LockStatus (var jsonString: String): StringDataConverter {
    OPEN("no"),
    CLOSED("yes"),
    OWNED("own");

    companion object: StringDataCreator<LockStatus> {
        override fun createFrom(string: String): LockStatus? {
            return values().firstOrNull {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}