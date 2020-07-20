package be.technifutur.devmob9.sdsftryone.webservice

class Player(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var number: Int = 0,
    var team: List<String> = listOf(),
    var action: Char? = 'A'
)