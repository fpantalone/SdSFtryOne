package be.technifutur.devmob9.sdsftryone.webservice

class UpdateMatchConfig(
    var champ: Int,
    var day: Int,
    var config: String
) : ModificationData {
    override var action: Char = 'U'
        set(_) {
            field = 'U'
        }
}