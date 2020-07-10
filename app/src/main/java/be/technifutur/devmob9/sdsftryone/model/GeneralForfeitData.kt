package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GeneralForfeitData(@PrimaryKey var id: Long=0, var champID: Int=0,
                              var clubCodeArray: RealmList<Int> = RealmList<Int>()
): RealmObject()