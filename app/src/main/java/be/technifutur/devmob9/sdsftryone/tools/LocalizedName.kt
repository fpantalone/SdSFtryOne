package be.technifutur.devmob9.sdsftryone.tools

import java.lang.IllegalArgumentException

class LocaliziedName (var fr: String, var nl: String, var en: String): StringDataConverter {

    @Throws (IllegalArgumentException::class)
    constructor(string: String) : this("", "", "") {
        //analyse de la regex si regex valide appeler this passer les valeur au constructeur par défaut
        fr = ""
        nl = ""
        en = ""

    }
}