package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChampData constructor(
    @PrimaryKey var ChampID: Int = 0, var champName: String="", var numDay: Int=0,
    var season: Int=0, var team: RealmList<String> = RealmList<String>()) : RealmObject() {

}