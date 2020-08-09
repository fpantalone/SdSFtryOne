package be.technifutur.devmob9.sdsftryone.dao

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import be.technifutur.devmob9.sdsftryone.model.*
import be.technifutur.devmob9.sdsftryone.webservice.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

class DbManager {

    companion object {
        lateinit var instance: DbManager private set
        private lateinit var context: Context
        private lateinit var dateTimeFormatter: SimpleDateFormat

        fun init(context: Context) {
            instance = DbManager()
            DbManager.context = context
            dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            Realm.init(context)
        }

        fun findClub(code: String): ClubData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(ClubData::class.java)
                .equalTo("code", code)
                .findFirst()
        }

        fun findChamp(id: Int): ChampData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(ChampData::class.java)
                .equalTo("id", id)
                .findFirst()
        }

        fun findDay(id: Int, champ: Int): DayData? {
            return findChamp(champ)?.days?.firstOrNull { it.id ==id }
        }

        fun findMatch(id: Int, day: Int, champ: Int): MatchData? {
            return findDay(day, champ)?.matches?.firstOrNull {it.id == id}
        }

        fun findPlayer(id: Int): PlayerData? {
            val realm = Realm.getDefaultInstance()
            return realm.where(PlayerData::class.java)
                .equalTo("id", id)
                .findFirst()
        }

        fun findEvent(id: Int, match: Int, day: Int, champ: Int): EventData? {
           return findMatch(match, day, champ)?.events?.firstOrNull { it.id == id }

        }

        fun findMatchPlayer(id: Int, match: Int, day: Int, champ: Int): MatchPlayerData? {
            return findChamp(champ)?.days?.firstOrNull {
                it.id == day
            }?.matches?.firstOrNull { it.id == match }?.players?.firstOrNull { it.id == id }
        }

        fun addClub(code: String, shortName: String, fullName: String, logo: String?): ClubData? {
            val realm = Realm.getDefaultInstance()
            var club: ClubData?
            try {
                club = realm.createObject(ClubData::class.java, code)

            } catch (e: RealmException) {
                club = findClub(code)
            }
            club?.let {
                club.shortName = shortName
                club.fullName = fullName
                club.logo = logo
            }

            return club
        }

        fun addChampTeam(team: String, code: String): ChampTeamData? {
            val realm = Realm.getDefaultInstance()

            try {
                val champTeam = realm.createObject(ChampTeamData::class.java)
                champTeam.team = team
                champTeam.code = code
                return champTeam
            } catch (e: RealmException) {
                Log.d("DBMANAGER", e.message ?: "addChampTeam")
            }
            return null
        }

        fun addChamp(
            id: Int, name: String, numDay: Int, season: Int, matchConfig: String,
            genForfeit: List<String>, teams: List<ChampTeamData>
        ): ChampData? {
            val realm = Realm.getDefaultInstance()
            var champ: ChampData?

            try {
                champ = realm.createObject(ChampData::class.java, id)
            } catch (e: RealmException) {
                champ = findChamp(id)
            }

            champ?.let {
                champ.name = name
                champ.numDay = numDay
                champ.season = season
                champ.matchConfig = matchConfig
                champ.teams.addAll(teams)
                champ.genForfeit.addAll(genForfeit)
            }

            return champ
        }

        fun addDay(
            id: Int, champ: Int, name: String, date: Date,
            comment: String
        ): DayData? {

            val realm = Realm.getDefaultInstance()
            var day: DayData?

            try {
                day = realm.createObject(DayData::class.java)

            } catch (e: RealmException) {
                day = findDay(id, champ)
            }

            day?.let {

                day.name = name
                day.date = date
                day.comment = comment
                val champData = findChamp(champ)
                champData?.days?.add(day)
            }

            return day

        }

        fun addMatch(
            id: Int, day: Int, champ: Int, date: Date?, hour: String, homeTeam: String,
            awayTeam: String, homeScore: Int?, awayScore: Int?, comment: String?, locked: String?
        ): MatchData? {

            val realm = Realm.getDefaultInstance()
            var match: MatchData?

            try {
                match = realm.createObject(MatchData::class.java)

            } catch (e: RealmException) {
                Log.d("DBMANAGER", e.message ?: "addMatch")
                match = findMatch(id, day, champ)
            }

            match?.let {
                match.date = date
                match.hour = hour
                match.homeTeam = homeTeam
                match.awayTeam = awayTeam
                match.homeScore = homeScore
                match.awayScore = awayScore
                match.comment = comment ?: ""
                match.locked = locked

                val matchDay = findDay(day, champ)
                matchDay?.matches?.add(match)
            }
            return match
        }

        fun addPlayer(
            id: Int, firstName: String, lastName: String,
            number: Int, team: List<String>
        ): PlayerData? {

            val realm = Realm.getDefaultInstance()
            var player: PlayerData?

            try {
                player = realm.createObject(PlayerData::class.java, id)
            } catch (e: RealmException) {
                player = findPlayer(id)
                Log.d("DBMANAGER", e.message ?: "addPlayer")
            }

            player?.let {
                player.firstName = firstName
                player.lastname = lastName
                player.number = number
                player.team.addAll(team)
            }

            return player
        }

        fun addEvent(
            id: Int, match: Int, day: Int, champ: Int, time: String,
            team: String, type: String, param: String
        ): EventData? {

            val realm = Realm.getDefaultInstance()
            var event: EventData?

            try {
                event = realm.createObject(EventData::class.java)
            } catch (e: RealmException) {
                event = findEvent(id, match, day, champ)
                Log.d("DBMANAGER", e.message ?: "addEventt")
            }

            event?.let {
                event.time = time
                event.team = team
                event.type = type
                event.team = team
                event.param = param
            }

            return event
        }

        fun addMatchPlayer(
            id: Int, match: Int, day: Int, champ: Int,
            player: Int, name: String, number: Int,
            status: Int
        ): MatchPlayerData? {
            val realm = Realm.getDefaultInstance()
            var matchPlayer: MatchPlayerData?

            try {
                matchPlayer = realm.createObject(MatchPlayerData::class.java)
            } catch (e: RealmException) {
                matchPlayer = findMatchPlayer(id, match, day, champ)
            }

            matchPlayer?.let {
                matchPlayer.player = findPlayer(player)
                matchPlayer.name = name
                matchPlayer.number = number
                matchPlayer.status = status
            }

            return matchPlayer
        }

        fun removeClub(code: String) {
            findClub(code)?.let { removeClub(it) }
        }

        fun removeClub(clubData: ClubData) {
            clubData.delete()
        }

        fun removeChamp(id: Int) {
            findChamp(id)?.let { removeChamp(it) }
        }

        fun removeChamp(champData: ChampData) {
            champData.delete()
        }

        fun removeDay(id: Int, champ: Int) {
            findDay(id, champ)?.let { removeDay(it) }
        }

        fun removeDay(day: DayData) {
            day.delete()
        }

        fun removeMatch(id: Int, day: Int, champ: Int) {
            findMatch(id, day, champ)?.let { removeMatch(it) }
        }

        fun removeMatch(match: MatchData) {
            match.delete()
        }

        fun removePlayer(id: Int) {
            findPlayer(id)?.delete()
        }

        fun removeEvent(id: Int, match: Int, day: Int, champ: Int) {
            findEvent(id, match, day, champ)?.let { removeEvent(it) }
        }

        fun removeEvent(event: EventData) {
            event.delete()
        }

        fun removeMatchPlayer(id: Int, match: Int, day: Int, champ: Int) {
            findMatchPlayer(id, match, day, champ)?.let { removeMatchPlayer(it) }
        }

        fun removeMatchPlayer(matchPlayer: MatchPlayerData) {
            matchPlayer.delete()
        }

        fun updateData (data: AllTable, onComplete: Consumer<Boolean>) {
            // passe dans le thread computation
            Single.create<Boolean> { emitter ->
                val realm = Realm.getDefaultInstance()
                try {
                    realm.beginTransaction()
                    // TODO enregistre les données dans la base de données
                    realm.commitTransaction()
                    emitter.onSuccess(true)
                }
                catch (ex: Exception) {
                    emitter.onError(ex)
                }
            }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete, Consumer { onComplete.accept(false) })
        }

        fun getTableUpdateTime(): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val lastDbUpdateTimeStr = preferences.getString("db_update_time", "")
            return if (lastDbUpdateTimeStr?.isBlank() != false) {
                null
            } else {
                lastDbUpdateTimeStr
            }
        }

        fun getAllChampData(): List<ChampData> {
            val realm = Realm.getDefaultInstance()
            return realm.where<ChampData>().findAll()
        }

        fun getCalendar(team: String): List<MatchData> {
            val matches = arrayListOf<MatchData>()

            getAllChampData().forEach { champ ->
                val teamInfos = champ.teams.where()
                    .equalTo("team", team)
                    .findFirst()

                teamInfos?.let {
                    var prefTeam = teamInfos.code.substring(teamInfos.code.length-1)
                    if ("ABC".indexOf(prefTeam) < 0 ) {
                        prefTeam = ""
                    }
                    champ.days.forEach { day ->
                        if (day.id >= teamInfos.firstDay) {
                            day.getMyClubMatch(prefTeam)?.let { match ->
                                matches.add(match)
                            }
                        }
                    }
                }
            }

            matches.sortWith(kotlin.Comparator { m1, m2  ->
                val d1 = m1.getMatchDate()
                val d2 = m2.getMatchDate()
                if (d1 == d2){
                    return@Comparator m1.hour.compareTo(m2.hour)
                }
                return@Comparator d1.compareTo(d2)
            })

            return matches
        }

        fun updateMatch(matchList: List<Match>) {
            for (match in matchList) {
                when (match.action) {
                    'A' -> {
                        var date: Date? = null
                        match.date?.let {
                            date = WebService.dateFormatter.parse(it)
                        }

                        addMatch(
                            match.id, match.day, match.champ, date,
                            match.hour, match.eq1, match.eq2, match.re1, match.re2,
                            match.comment ?: "", match.locked
                        )
                    }
                    'U' -> {
                        val dbMatch = findMatch(match.id, match.day, match.champ)
                        dbMatch?.hour = match.hour
                        dbMatch?.homeTeam = match.eq1
                        dbMatch?.awayTeam = match.eq2
                        dbMatch?.homeScore = match.re1
                        dbMatch?.awayScore = match.re2
                        dbMatch?.comment = match.comment ?: ""
                        dbMatch?.locked = match.locked
                    }
                    'R' -> {
                        removeMatch(match.id, match.day, match.champ)
                    }
                }
            }
        }

        fun updateDay(dayList: List<Day>) {

            for (day in dayList) {
                when (day.action) {
                    'A' -> {
                        WebService.dateFormatter.parse(day.date)?.let { date ->
                            addDay(
                                    day.id, day.champ, day.name ?: "", date,
                                    day.comment ?: ""
                                )
                        }
                    }

                    'U' -> {
                        val dbDay = findDay(day.id, day.champ)
                        dbDay?.name = day.name ?: ""
                        dbDay?.date = WebService.dateFormatter.parse(day.date) ?: Date()
                        dbDay?.comment = day.comment ?: ""
                    }
                    'R' -> {
                        removeDay(day.id, day.champ)
                    }
                }
            }
        }

        fun updateChamp(champList: List<Champ>) {
            for (champ in champList) {
                when (champ.action) {
                    'A' -> {
                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        addChamp(
                            champ.id, champ.name, champ.numDay,
                            champ.season, champ.matchConfig, champ.genForfeit, teams
                        )
                    }
                    'U' -> {

                        val teams = ArrayList<ChampTeamData>()

                        champ.team?.let {
                            val champTeam = addChampTeam(it, "#00016")
                            if (champTeam != null) {
                                teams.add(champTeam)
                            }
                        }

                        champ.teams?.let {
                            for (team in it) {
                                val champTeam =
                                    addChampTeam(team.team, team.code)
                                if (champTeam != null) {
                                    teams.add(champTeam)
                                }
                            }
                        }

                        val dbChamp = findChamp(champ.id)

                        dbChamp?.name = champ.name
                        dbChamp?.numDay = champ.numDay
                        dbChamp?.season = champ.season
                        dbChamp?.genForfeit?.addAll(champ.genForfeit)
                        dbChamp?.teams?.addAll(teams)
                    }
                    'R' -> {
                        removeChamp(champ.id)
                    }
                }

            }
        }

        fun updateClub(clubList: List<Club>) {
            for (club in clubList) {
                when (club.action) {
                    'A' -> {
                        addClub(club.code, club.short, club.full, club.logo)

                    }
                    'U' -> {
                        val dbClub = findClub(club.code)
                        dbClub?.shortName = club.short
                        dbClub?.fullName = club.full
                        dbClub?.logo = club.logo
                    }
                    'R' -> {
                        removeClub(club.code)
                    }
                }
            }
        }

        fun updatePlayer(playerList: List<Player>?) {
            if (null == playerList) {
                return
            }
            for (player in playerList) {
                when (player.action) {
                    'A' -> {
                        addPlayer(
                            player.id,
                            player.firstName,
                            player.lastName,
                            player.number,
                            player.team
                        )
                    }
                    'U' -> {
                        val dbPlayer = findPlayer(player.id)
                        dbPlayer?.firstName = player.firstName
                        dbPlayer?.firstName = player.firstName
                        dbPlayer?.number = player.number
                        dbPlayer?.team?.addAll(player.team)
                    }
                    'R' -> {
                        removePlayer(player.id)
                    }
                }
            }
        }

        fun updateEvent(eventList: List<Event>?) {
            if (null == eventList) {
                return
            }
            for (event in eventList) {
                when (event.action) {
                    'A' -> {
                        addEvent(
                            event.id, event.match, event.day, event.champ,
                            event.time, event.team, event.type, event.param
                        )
                    }
                    'U' -> {
                        val dbEvent =
                            findEvent(event.id, event.match, event.day, event.champ)
                        dbEvent?.time = event.time
                        dbEvent?.team = event.team
                        dbEvent?.type = event.type
                        dbEvent?.param = event.param
                    }
                    'R' -> {
                        removeEvent(event.id, event.match, event.day, event.champ)
                    }
                }
            }
        }

        fun updateMatchPlayer(matchPlayerList: List<MatchPlayer>?) {
            if (null == matchPlayerList) {
                return
            }
            for (matchPlayer in matchPlayerList) {
                when (matchPlayer.action) {
                    'A' -> {
                        addMatchPlayer(
                            matchPlayer.id,
                            matchPlayer.match,
                            matchPlayer.day,
                            matchPlayer.champ,
                            matchPlayer.player,
                            matchPlayer.name,
                            matchPlayer.number,
                            matchPlayer.status
                        )
                    }
                    'U' -> {
                        val dbMatchPlayer = findMatchPlayer(
                            matchPlayer.id,
                            matchPlayer.match,
                            matchPlayer.day,
                            matchPlayer.champ
                        )
                        // on ne chamge pas le player
                        dbMatchPlayer?.name = matchPlayer.name
                        dbMatchPlayer?.number = matchPlayer.number
                        dbMatchPlayer?.status = matchPlayer.status
                    }
                    'R' -> {
                        removeMatchPlayer(
                            matchPlayer.id,
                            matchPlayer.match,
                            matchPlayer.day,
                            matchPlayer.champ
                        )
                    }
                }
            }
        }

    } // fin companion Object


    fun getAllClubData(): List<ClubData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<ClubData>().findAll()
    }

    fun getAllDayData(): List<DayData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<DayData>().findAll()
    }

    fun getAlltMatchData(): List<MatchData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchData>().findAll()
    }

    fun getAllEventData(): List<EventData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<EventData>().findAll()
    }

    fun getAllMatchPlayerData(): List<MatchPlayerData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<MatchPlayerData>().findAll()
    }

    fun getallPlayerData(): List<PlayerData> {
        val realm = Realm.getDefaultInstance()
        return realm.where<PlayerData>().findAll()
    }
}