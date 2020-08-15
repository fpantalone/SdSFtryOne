package be.technifutur.devmob9.sdsftryone.webservice

class Day(
    var id: Int = 0,
    var champ: Int = 0,
    var name: String? = null,
    var date: String = "",
    var comment: String? = null,
    var matchConfig: String? = null,
    override var action: Char = 'A'
): Action