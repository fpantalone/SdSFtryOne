package be.technifutur.devmob9.sdsftryone.tools

class SubstitutionEventParam(var inPlayerId: Int, var outPlayerId: Int) : EventParam {

    init {
        if (0 >= inPlayerId || 0 >= outPlayerId || inPlayerId == outPlayerId) {
            throw IllegalArgumentException()
        }
    }

    companion object : StringDataCreator<SubstitutionEventParam> {
        override fun createFrom(string: String): SubstitutionEventParam? {
            val regex = Regex("^I([1-9]\\d*)O([1-9]\\d*)$")
            val result = regex.matchEntire(string) ?: return null
            val inPlayerId = result.groupValues[1].toInt()
            val outPlayerId = result.groupValues[2].toInt()

            if (inPlayerId != outPlayerId) {
                return SubstitutionEventParam(inPlayerId, outPlayerId)
            }
            return null
        }
    }

    override fun toString(): String {
        return "I${inPlayerId}O${outPlayerId}"
    }
}