package be.technifutur.devmob9.sdsftryone.tools

enum class ChronoEventType(val jsonString: String): StringDataConverter {
    BEGIN("B"),
    END("E"),
    PAUSE("P"),
    RESUME("R"),
    STOP("S"),
    CANCEL("C");

    companion object: StringDataCreator<ChronoEventType> {
        override fun createFrom(string: String): ChronoEventType? {
            return values().first {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}