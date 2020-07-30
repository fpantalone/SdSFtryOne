package be.technifutur.devmob9.sdsftryone.tools

import java.lang.IllegalArgumentException

class GoalEventParam(
    var scorerId: Int, var assistPlayerId: Int = 0,
    var isPenalty: Boolean = false
) : EventParam {

    init {
        if (scorerId <= 0 || assistPlayerId < 0) {
            throw IllegalArgumentException()
        }
    }

    companion object : StringDataCreator<GoalEventParam> {
        override fun createFrom(string: String): GoalEventParam? {
            val regex = Regex("^G(\\d+)(?:A(\\d+)|(P))?$")
            val result = regex.matchEntire(string) ?: return null
            val scorerId = result.groupValues[1].toInt()
            val assist = result.groupValues[2]
            val assistPlayerId: Int

            if (assist.isNotEmpty()) {
                assistPlayerId = result.groupValues[2].toInt()
            } else {
                assistPlayerId = 0
            }

            val isPenalty = result.groupValues[3].isNotEmpty()

            return GoalEventParam(scorerId, assistPlayerId, isPenalty)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder().append("G")
            .append(scorerId)
        if (assistPlayerId != 0) {
            sb.append("A")
                .append(assistPlayerId)
        }
        if (isPenalty) {
            sb.append("P")
        }
        return sb.toString()
    }
}
