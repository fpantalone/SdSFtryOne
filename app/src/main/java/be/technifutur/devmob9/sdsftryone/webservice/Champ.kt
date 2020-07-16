package be.technifutur.devmob9.sdsftryone.webservice

class Champ (
    var id: Int = 0,
    var name: String = "",
    var numDay: Int = 0,
    var season: Int = 0,
    var matchConfig: String = "",
    var genForfeit: List<String> = listOf(),
    var team: String? = null,
    var teams: List <teamList>? = null,
    var action: Char = 'A'
) {
    inner class teamList(
        var team: String = "",
        var code: String = ""
    ) {

    }
}
