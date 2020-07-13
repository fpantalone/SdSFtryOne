package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ClubData(
    @PrimaryKey var code: String = "",
    var shortName: String = "",
    var fullName: String = "",
    var logo: String = ""
) : RealmObject()