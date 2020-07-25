package be.technifutur.devmob9.sdsftryone.tools

class NameComment(
    var nbHomeShoot: Int = 0,
    var nbAwayShoot: Int = 0,
    var matchType: String = "",
    var frenchComment: String = "",
    var dutchComment: String = "",
    var engComment: String = ""
) {

    companion object {

        private val regex =
            Regex("\"(?:\\(([0-9]+) ?- ?([0-9]+)\\))?((?:rem|live|stop|fft)!)?(.*?)(?:\\^(.*?)(?:\\^(.*?))?)?\$\"")

        fun getNameComment(string: String): NameComment? {

            val nameComment = NameComment()
            val result = regex.matchEntire(string) ?: return null

            nameComment.nbHomeShoot = result.groupValues[1].toInt()
            nameComment.nbAwayShoot = result.groupValues[2].toInt()
            nameComment.matchType = result.groupValues[3]
            nameComment.frenchComment = result.groupValues[4]
            nameComment.dutchComment = result.groupValues[5]
            nameComment.engComment = result.groupValues[6]

            return nameComment
        }
    }

    override fun toString(): String {

        val sb = StringBuilder().append("(")
            .append(nbHomeShoot)
            .append("-")
            .append(nbAwayShoot)
            .append(")")
            .append(matchType)
            .append("^")
            .append(frenchComment)
            .append("^")
            .append(dutchComment)
            .append("^")
            .append(engComment)

        return sb.toString()
    }

}

// exemple commentaire français / neerlandais / anglais
// "1/4 de finale^1/4 finale^Round of 8"