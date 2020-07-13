package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GeneralForfeitData(
    var champ: ChampData = ChampData(),
    var club: ClubData = ClubData()
) : RealmObject()