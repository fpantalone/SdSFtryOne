package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TableUpdateTimers( @PrimaryKey var tableName: String = "",
    var updateTime: String = "") : RealmObject()