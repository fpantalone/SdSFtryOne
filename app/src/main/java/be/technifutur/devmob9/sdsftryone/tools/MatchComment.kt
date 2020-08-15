package be.technifutur.devmob9.sdsftryone.tools

class MatchComment(
    fr: String, nl: String, en: String, var homePenalty: Int?,
    var awayPenalty: Int?, var status: MatchStatus
) : LocalizedName(fr, nl, en) {

    companion object : StringDataCreator<MatchComment> {
        override fun createFrom(string: String): MatchComment? {
            val regex = Regex("^(?:\\((\\d+) ?- ?(\\d+)\\))?((?:rem|live|stop|fft)!)?(.*)\$")
            val result = regex.matchEntire(string)!!
            val matchComment = MatchComment("", "", "", null, null, MatchStatus.NONE)

            val hPen = result.groupValues[1]
            if (hPen.isEmpty()) {
                matchComment.homePenalty = null
            } else {
                matchComment.homePenalty = hPen.toInt()
            }

            val aPen = result.groupValues[2]

            if (aPen.isEmpty()) {
                matchComment.awayPenalty = null
            } else {
                matchComment.awayPenalty = result.groupValues[2].toInt()
            }

            matchComment.status = MatchStatus.createFrom(result.groupValues[3]) ?: MatchStatus.NONE
            matchComment.parse(result.groupValues[4])

            return matchComment
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        if (null != homePenalty && null != awayPenalty) {
            sb.append("(")
                .append(homePenalty!!)
                .append("-")
                .append(awayPenalty!!)
                .append(")")
        }
        sb.append(status.jsonString)
        sb.append(super.toString())
        return sb.toString()
    }
}
