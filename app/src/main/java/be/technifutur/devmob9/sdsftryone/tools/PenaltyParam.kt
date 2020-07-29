package be.technifutur.devmob9.sdsftryone.tools

import java.lang.IllegalArgumentException
import java.lang.StringBuilder

class PenaltyParam(var isSuccess: Boolean, var playerId: Int): EventParam {

    init {
        if (playerId <=0) {
            throw IllegalArgumentException()
        }
    }

    companion object: StringDataCreator<PenaltyParam> {
        override fun createFrom(string: String): PenaltyParam? {
            val isSuccess: Boolean
            val playerid: Int
            val regex =  Regex ("^([FS])([1-9]\\d*)$")
            val result = regex.matchEntire(string) ?: return null

            isSuccess = "S" == result.groupValues[1]
            playerid = result.groupValues[2].toInt()

            return PenaltyParam(isSuccess,playerid)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        if (isSuccess) {
            sb.append("S")
        } else {
            sb.append("F")
        }
        sb.append(playerId)

        return sb.toString()
    }
}
