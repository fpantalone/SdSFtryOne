package be.technifutur.devmob9.sdsftryone.model

import be.technifutur.devmob9.sdsftryone.BuildConfig
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ClubData(
    @PrimaryKey var code: String = "",
    var shortName: String = "",
    var fullName: String = "",
    var logo: String? = null

) : RealmObject() {
    var suffix: String = ""
        get() = if (field.isEmpty()) { "" } else { " $field" }
        set(value) {
            field = value
        }

    fun getSuffixedShortName (): String {
        return shortName+suffix
    }

    fun getSuffixedFullName (): String {
        return fullName+suffix
    }

    fun getLogoURL (): String? {
        if (null != logo)
        {
            return BuildConfig.IMAGE_URL+logo+".png"
        }
        else {
            return null
        }
    }

    fun delete () {
        this.deleteFromRealm()
    }
}