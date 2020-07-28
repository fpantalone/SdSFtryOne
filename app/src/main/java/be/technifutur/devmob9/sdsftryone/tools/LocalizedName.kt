package be.technifutur.devmob9.sdsftryone.tools

import java.lang.StringBuilder

open class LocaliziedName(var fr: String, var nl: String, var en: String) : StringDataConverter {

//    @Throws (IllegalArgumentException::class)
//    constructor(string: String) : this("", "", "") {
//        //analyse de la regex si regex valide appeler this passer les valeur au constructeur par défaut
//
//        val regex = Regex("^(.*?)(?:(\\^)(.*?)(?:(\\^)(.*?))?)?\$")
//        val result = regex.matchEntire(string)
//
//        fr = ""
//        nl = ""
//        en = ""
//
//    }

    companion object : StringDataCreator<LocaliziedName> {
        val regex = Regex("^(.*?)(?:\\^(.*?)(?:\\^(.*?))?)?\$")
        override fun createFrom(string: String): LocaliziedName? {
            val localiziedName = LocaliziedName("","","")
            localiziedName.parse(string)
            return localiziedName
        }
    }

    override fun toString(): String {
        val sb = StringBuilder().append(fr)
                if (nl.isNotEmpty() || en.isNotEmpty()) {
                    sb.append("^").append(nl)
                    if (en.isNotEmpty()) {
                        sb.append("^").append(en)
                    }
                }

        return sb.toString()
    }

    fun parse(string: String){
        val result = regex.matchEntire(string)
        fr = result?.groupValues?.get(1) ?: ""
        nl = result?.groupValues?.get(2) ?: fr
        en = result?.groupValues?.get(3) ?: fr
    }
}