package be.technifutur.devmob9.sdsftryone.webservice

class champ(
    var id: Int = 0,
    var name: String = "",
    var numDay: Int = 0,
    var season: Int = 0,
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