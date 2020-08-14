package be.technifutur.devmob9.sdsftryone.tools

open class LocalizedName(var fr: String, var nl: String, var en: String) : StringDataConverter {


    companion object : StringDataCreator<LocalizedName> {
        val regex = Regex("^(.*?)(?:\\^(.*?)(?:\\^(.*?))?)?\$")
        override fun createFrom(string: String): LocalizedName {
            val localiziedName = LocalizedName("", "", "")
            localiziedName.parse(string)
            return localiziedName
        }
    }

    operator fun get(lang: String): String {
        return when (lang) {
            "en" -> en

            "nl" -> nl

            else -> fr
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

    fun parse(string: String) {
        val result = regex.matchEntire(string)
        fr = result?.groupValues?.get(1) ?: ""
        nl = result?.groupValues?.get(2) ?: fr
        en = result?.groupValues?.get(3) ?: fr
    }
}