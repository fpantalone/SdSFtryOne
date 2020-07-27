package be.technifutur.devmob9.sdsftryone.webservice

class Champ(
    var id: Int = 0,
    var name: String = "",
    var numDay: Int = 0,
    var season: Int = 0,
    var matchConfig: String = "",
    var genForfeit: List<String> = listOf(),
    var team: String? = null,
    var teams: List<teamItem>? = null,
    var action: Char = 'A'
) {
    inner class teamItem(
        var team: String = "",
        var code: String = "",
        var firstDay: Int = 1
    )
}
