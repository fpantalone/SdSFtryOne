package be.technifutur.devmob9.sdsftryone.tools

class MatchConfig(
    var nbPlayer: Int = 0,
    var nbChange: Int = 0,
    var nbPeriod: Int = 0,
    var minPerPeriod: Int = 0,
    var prologationLength: Int = 0,
    var isEliminatory: Boolean = false
) {

    companion object {
        private val regex =
            Regex("\"^([5-9]|1[01])\\+(\\d)D([1-6])×([5-9]|[1-3]\\d|4[0-5])(?:P([5-9]|1[0-5]))?(E)?\$\"")


        fun getMatchConfig(string: String): MatchConfig? {
            val matchConfig = MatchConfig()
            val result = regex.matchEntire(string) ?: return null

            matchConfig.nbPlayer = result.groupValues[1].toInt()
            matchConfig.nbChange = result.groupValues[2].toInt()
            matchConfig.nbPeriod = result.groupValues[3].toInt()
            matchConfig.minPerPeriod = result.groupValues[4].toInt()
            if ("E" == result.groupValues[5]) {
                matchConfig.isEliminatory = true
            }

            return matchConfig
        }
    }

    override fun toString(): String {
        val sb = StringBuilder().append(nbPlayer)
            .append("+")
            .append(nbChange)
            .append("D")
            .append(nbPeriod)
            .append("×")
            .append(minPerPeriod)

        if (0 != prologationLength) {
            sb.append("P")
                .append(prologationLength)
        }

        if (isEliminatory) {
            sb.append("E")
        }
        return sb.toString()
    }
}