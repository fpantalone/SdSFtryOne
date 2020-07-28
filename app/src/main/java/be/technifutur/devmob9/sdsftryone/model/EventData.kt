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
                return ChronoParam.createFrom (param)
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

        if (side == TeamSide.HOME || type != EventType.CHRONO) {
            return false
        }

        val mySide = match?.first()?.getMySide() ?: return false

        if (side != mySide && type == EventType.SUBSTITUTION || type == EventType.CARD) {
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
            event.type = type.jsonType
            return this
        }

        fun setParam (eventParam: EventParam): EventBuilder {
            event.param = eventParam.toString()
            return this
        }

        fun build (): EventData? {
            event.validate() ?:return null
            val realm = Realm.getDefaultInstance()

            realm.beginTransaction()
            val eventData = realm.copyToRealm(event)
            match.events.add(event)
            realm.commitTransaction()

            return eventData
        }
    }
}
