package be.technifutur.devmob9.sdsftryone.tools

class ChronoEventParam(var type: ChronoEventType) : EventParam {

    companion object : StringDataCreator<ChronoEventParam> {
        override fun createFrom(string: String): ChronoEventParam? {

            ChronoEventType.createFrom(string)?.let {
                return ChronoEventParam(it)
            }
            return null
        }
    }
}
