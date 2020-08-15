package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.tools.*
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects

open class EventData(
    @Index var id: Int = 0,
    var time: String = "",
    var team: String = "",
    var type: String = "",
    var param: String = "",
    @LinkingObjects("events")
    val match: RealmResults<MatchData>? = null
) : RealmObject() {

    companion object {
        fun validateTime (time: String, type: EventType, param: String): Boolean {
            val chronoType = ChronoEventParam.createFrom(param)?.type
            val valideHourChronoType = arrayOf(ChronoEventType.BEGIN, ChronoEventType.RESUME, ChronoEventType.CANCEL)
            val regexMatchHour = Regex("^(\\d)@(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d\$")
            val matchHour = regexMatchHour.matchEntire(time)
            if (null != matchHour && type == EventType.CHRONO && null != chronoType && chronoType in valideHourChronoType) {
                val periodZero = "0" == matchHour.groupValues[1]
                return if (chronoType == ChronoEventType.CANCEL) periodZero else !periodZero
            }
            val validePenaltyChronoType = arrayOf(ChronoEventType.BEGIN, ChronoEventType.END)
            val regexMatchPenalty = Regex("^[1-9]@\\d+-\\d+(\\(\\d+-\\d+\\))?\$")
            val matchPenalty = regexMatchPenalty.matchEntire(time)
            if (null != matchPenalty && (type == EventType.PENALTY || (type == EventType.CHRONO && validePenaltyChronoType.contains(chronoType)))) {
                if (matchPenalty.groupValues[1].isNotEmpty()) {
                    return (chronoType != ChronoEventType.BEGIN || "(0-0)" == matchPenalty.groupValues[1])
                }
                return chronoType == ChronoEventType.END
            }
            val regexMatchTime  = Regex("^[1-8]@\\d{1,3}(:00\\+(\\d|1[0-5]))?:[0-5]\\d\$")
            if (null != regexMatchTime.matchEntire(time)) {
                return type != EventType.PENALTY && (type != EventType.CHRONO || !valideHourChronoType.contains(chronoType))
            }
            return false;
        }
    }

    fun getType (): EventType? {
        try {
            return EventType.valueOf(type)
        }
        catch (e: IllegalArgumentException) {
            return null
        }
    }

    fun getSide() : TeamSide? {
        try {
            return TeamSide.valueOf(team)
        }
        catch (e: IllegalArgumentException) {
            return null
        }
    }

    fun getParam (): EventParam? {
        val type = getType() ?: return null

        when (type) {
            EventType.GOAL -> {
                return GoalEventParam.createFrom (param)
            }
            EventType.SUBSTITUTION -> {
                return SubstitutionEventParam.createFrom (param)
            }
            EventType.CARD -> {
                return CardEventParam.createFrom (param)
            }
            EventType.PENALTY -> {
                return PenaltyParam.createFrom (param)
            }
            EventType.CHRONO -> {
                return ChronoEventParam.createFrom (param)
            }
            else -> {
                return EmptyEventParam.createFrom (param)
            }
        }
    }

    fun getDisplayTime (): String? {

        val regex = Regex ("^(\\d+)(?::00\\+(\\d+))?:(\\d{2})$")
        val result = regex.matchEntire(time) ?: return null
        var time = result.groupValues[1].toInt()

        val inc = if (result.groupValues[3].toInt() > 0) {1} else {0}

        if (!result.groupValues[3].isBlank()) {
            time = result.groupValues[3].toInt() + inc
            return time.toString()
        }
        time += inc
        return time.toString()
    }

    fun validate (): Boolean {
        val side = getSide() ?: return false
        val type = getType() ?: return false
        getParam() ?: return false
        getDisplayTime() ?: return false

        if (side == TeamSide.AWAY || type == EventType.CHRONO) {
            return false
        }

        val mySide = match?.first()?.getMySide() ?: return false

        if (side != mySide && (type == EventType.SUBSTITUTION || type == EventType.CARD)) {
            return false
        }
        return true
    }

    fun getBuilder (match: MatchData): EventBuilder {
        return  EventBuilder(match)
    }


    inner class EventBuilder(val match: MatchData) {
        private var event = EventData()

        init {
            val id = (match.events.max("id")?.toInt()?:0)+1
            event.id = id

            //TODO: event.time = timeServer.getTime()
        }

        fun setType (type: EventType): EventBuilder {
            event.type = type.jsonString
            return this
        }

        fun setSide (side: TeamSide): EventBuilder {
            event.team = side.jsonString
            return this
        }

        fun setParam (eventParam: EventParam): EventBuilder {
            event.param = eventParam.toString()
            return this
        }

        fun build (): EventData? {
            if (!event.validate()) {
                return null
            }
            val realm = Realm.getDefaultInstance()

            realm.beginTransaction()
            val eventData = realm.copyToRealm(event)
            match.events.add(event)
            realm.commitTransaction()

            return eventData
        }
    }

    fun delete() {
        this.deleteFromRealm()
    }
}
