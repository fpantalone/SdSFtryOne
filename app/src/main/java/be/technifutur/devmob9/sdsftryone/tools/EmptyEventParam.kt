package be.technifutur.devmob9.sdsftryone.tools

class EmptyEventParam():StringDataConverter {

    companion object: StringDataCreator<EmptyEventParam> {
        override fun createFrom(string: String): EmptyEventParam? {
           val emptyEventParam = EmptyEventParam()

            // todo a complèter
            return emptyEventParam
        }
    }

}
