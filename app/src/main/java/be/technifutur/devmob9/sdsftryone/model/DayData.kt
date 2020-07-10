package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DayData(@PrimaryKey var DayID: Int=0, var ChampID: Int=0, var DayName: String="",
                   var DayDate: String="", var DayComment: String=""): RealmObject()