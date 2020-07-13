package be.technifutur.devmob9.sdsftryone.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class TableUpdateTimersData(
    @PrimaryKey var tableName: String = "",
    var updateTime: Date = Date()
) : RealmObject()