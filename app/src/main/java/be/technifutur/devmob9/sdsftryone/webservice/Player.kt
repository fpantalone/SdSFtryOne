package be.technifutur.devmob9.sdsftryone.webservice

class Player(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var number: Int? = null,
    var team: List<String> = listOf(),
    override var action: Char = 'A'
): Action