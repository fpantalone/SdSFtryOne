package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

open class DayData(
    @Index var id: Int = 0,
    var champ: ChampData = ChampData(),
    var name: String = "",
    var date: String = "",
    var comment: String = ""
) : RealmObject()