package be.technifutur.devmob9.sdsftryone.webservice

class Match(
    var id: Int = 0,
    var day: Int = 0,
    var champ: Int = 0,
    var date: String? = null,
    var hour: String = "",
    var eq1: String = "",
    var eq2: String = "",
    var re1: Int? = null,
    var re2: Int? = null,
    var comment: String? = null,
    var locked: String? = null,
    override var action: Char = 'A'
): Action