package be.technifutur.devmob9.sdsftryone.tools

import be.technifutur.devmob9.sdsftryone.R

class MatchConfigConverter : StringConverter.ConverterInterface<MatchConfig> {
    override fun parse(string: String): MatchConfig {

        val matchConfig: MatchConfig = MatchConfig()

        // sample matchConfig "11+3D2×45"

        val regex = Regex("\"^([5-9]|1[01])\\\\+(\\\\d)D([1-6])×([5-9]|[1-3]\\\\d|4[0-5])(?:P([5-9]|1[0-5]))?(E)?\\\$\"")

        val result =  regex.matchEntire(string)

            // TODO: remplir matchConfig


        return matchConfig
    }

    override fun format(t: MatchConfig): String {

        return t.nbPlayer.toString() + "+" + t.nbChange.toString() + "D" + t.nbPeriod.toString() + "'\'u00d" + t.periodLength.toString()
    }
}
