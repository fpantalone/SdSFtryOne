package be.technifutur.devmob9.sdsftryone.model

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.delete

open class PlayerData(
    @PrimaryKey var id: Int = 0,
    var firstName: String = "",
    var lastname: String = "",
    var number: Int = 0,
    var team: RealmList<String> = RealmList()
) : RealmObject() {

    val fullName: String
        get() {return "$firstName $lastname"}

    fun delete () {
        this.deleteFromRealm()
    }
}