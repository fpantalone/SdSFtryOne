package be.technifutur.devmob9.sdsftryone.tools

class EmptyEventParam() : EventParam {

    companion object : StringDataCreator<EmptyEventParam> {
        override fun createFrom(string: String): EmptyEventParam? {

            if (string.isEmpty()) {
                return EmptyEventParam()
            } else {
                return null
            }
        }
    }

    override fun toString(): String {
        return ""
    }
}
