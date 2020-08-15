package be.technifutur.devmob9.sdsftryone.webservice

class Club(
    var code: String = "",
    var short: String = "",
    var full: String = "",
    var logo: String? = null,
    override var action: Char = 'A'
): Action