package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.R
import be.technifutur.devmob9.sdsftryone.tools.LocalizedName
import be.technifutur.devmob9.sdsftryone.tools.MatchConfig
import be.technifutur.devmob9.sdsftryone.tools.RessourceReader
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Ignore
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import java.util.*
import kotlin.math.max
import kotlin.math.min

open class DayData(
    @Index var id: Int = 0,
    var name: String = "",
    var date: Date = Date(),
    var comment: String = "",
    var matches: RealmList<MatchData> = RealmList(),
    var matchConfig: String? = null,
    @LinkingObjects("days")
    val champ: RealmResults<ChampData>? = null
) : RealmObject() {
    @Ignore
    private var preferedTeam: String? = null
    @Ignore
    private var teamlist: List<String> = listOf()

    fun getMyClubMatch(preferedTeam: String): MatchData? {
        if (this.preferedTeam != preferedTeam || teamlist.isEmpty()) {
            this.preferedTeam = preferedTeam
            val suffix = preferedTeam
            teamlist = champ?.firstOrNull()?.teams?.map { it.code }
                ?.sortedWith(Comparator { t1, t2 ->
                    if (suffix.isNotEmpty()) {
                        if (t1.endsWith(suffix)) {
                            return@Comparator -1
                        }
                        if (t2.endsWith(suffix)) {
                            return@Comparator 1
                        }
                    }
                    return@Comparator t1.compareTo(t2)
                }) ?: listOf()
        }
        var foundIndex = teamlist.size
        var foundMatch: MatchData? = matches.first()

        for (match in matches) {
            val homeIndex = teamlist.indexOf(match.homeTeam)
            val awayIndex = teamlist.indexOf(match.awayTeam)
            val maxIndex = max(homeIndex,awayIndex)
            val minIndex = min(homeIndex,awayIndex)

            if (0 <= maxIndex) {
                val prefIndex = if (-1 == minIndex ) {maxIndex} else {minIndex}
                if (0 == prefIndex) {
                    return match
                }
                if(prefIndex < foundIndex) {
                    foundIndex = prefIndex
                    foundMatch = match
                }
            }
        }

        return foundMatch
    }


    fun getMatchConfig (): MatchConfig? {

        return  MatchConfig.createFrom(matchConfig?: champ?.firstOrNull()?.matchConfig ?: "")
    }


    fun getName(locale: Locale, shortStyle: Boolean = false): String {

        val localizedName = LocalizedName.createFrom(name)
        val locName = localizedName[locale.language]

        if (locName.isEmpty()) {
            if (shortStyle) {
                return id.toString()
            }
            else {
               return RessourceReader.getString(R.string.day_name_format, id)
            }
        }
        return locName
    }

    fun isPostpone (): Boolean {
        // TODO: to be implemented
        return false
    }

    fun delete () {
        matches.forEach { it.delete() }
        this.delete()
    }
}