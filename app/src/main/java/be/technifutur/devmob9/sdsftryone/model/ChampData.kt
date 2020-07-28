package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.tools.LocaliziedName
import be.technifutur.devmob9.sdsftryone.tools.MatchConfig
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class ChampData(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var numDay: Int = 0,
    var season: Int = 0,
    var matchConfig: String = "",
    var genForfeit: RealmList<String> = RealmList(),
    var teams: RealmList<ChampTeamData> = RealmList(),
    var days: RealmList<DayData> = RealmList()
) : RealmObject() {

    fun getMatchConfig(): MatchConfig? {
        return MatchConfig.createFrom(matchConfig)
    }

    fun getName(locale: Locale): String {

        val localizedName = LocaliziedName(name)

        return when (locale.language) {

            "nl" -> {
                localizedName.nl
            }
            "en" -> {
                localizedName.fr
            }
            else -> {
                localizedName.fr
            }
        }
    }
}