package be.technifutur.devmob9.sdsftryone.tools

enum class CardType(val jsonString: String): StringDataConverter {
    YELLOW("Y"),
    SECOND_YELLOW("E"),
    RED("R");

    companion object: StringDataCreator<CardType> {
        override fun createFrom(string: String): CardType? {
            return values().first {
                it.jsonString == string
            }
        }
    }

    override fun toString(): String {
        return jsonString
    }
}