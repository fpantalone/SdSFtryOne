package be.technifutur.devmob9.sdsftryone.tools

class MatchComment(
    fr: String, nl: String, en: String, var homePenalty: Int?,
    var awayPenalty: Int?, var status: MatchStatus
) : LocaliziedName(fr, nl, en) {

    companion object : StringDataCreator<MatchComment> {
        override fun createFrom(string: String): MatchComment? {
            val regex = Regex("^(?:\\((\\d+) ?- ?(\\d+)\\))?((?:rem|live|stop|fft)!)?(.*)\$")
            val result = regex.matchEntire(string)
            val matchComment = MatchComment("", "", "", null, null, MatchStatus.NONE)

            matchComment.homePenalty = result?.groupValues?.get(1)?.toInt()
            matchComment.awayPenalty = result?.groupValues?.get(2)?.toInt()
            matchComment.status = MatchStatus.valueOf(result?.groupValues?.get(3) ?: "")
            matchComment.parse(result?.groupValues?.get(4) ?: "")

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
