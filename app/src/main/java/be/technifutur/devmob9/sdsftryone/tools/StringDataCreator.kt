package be.technifutur.devmob9.sdsftryone.tools

interface StringDataCreator <T:StringDataConverter>{

    fun createFrom(string: String): T?

}