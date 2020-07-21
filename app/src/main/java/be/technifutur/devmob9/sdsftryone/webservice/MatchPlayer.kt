package be.technifutur.devmob9.sdsftryone.webservice

class MatchPlayer(
    var id: Int = 0,
    var match: Int = 0,
    var day: Int = 0,
    var champ: Int = 0,
    var player: Int = 0,
    var name: String = "",
    var number: Int = 0,
    var status: Int = 0,
    override var action: Char = 'A'):ModificationData
