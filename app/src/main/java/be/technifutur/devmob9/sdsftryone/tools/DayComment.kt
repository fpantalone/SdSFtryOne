package be.technifutur.devmob9.sdsftryone.tools

import java.lang.StringBuilder

class DayComment(fr: String, nl: String, en: String, var isPostpone: Boolean) :
    LocalizedName(fr, nl, en) {

    companion object : StringDataCreator<DayComment> {
        override fun createFrom(string: String): DayComment? {
            val regex = Regex("^(rem!)?(.*)\$")
            val result = regex.matchEntire(string)
            val dayComment = DayComment("", "", "", false)

            if (result?.groupValues?.get(1)?.isNotEmpty()!!){
                dayComment.isPostpone = true
            }
            dayComment.parse(result.groupValues[2])
            return dayComment
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        if (isPostpone) {
            sb.append("rem!")
        }
        sb.append(super.toString())

        return sb.toString()
    }
}