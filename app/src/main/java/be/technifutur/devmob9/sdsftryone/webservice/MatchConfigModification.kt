package be.technifutur.devmob9.sdsftryone.webservice

class MatchConfigModification (
    val day: Int,
    val champ: Int,
    val config: String,
    override var action: Char = 'U'
): Modification {
    override val modificationType = "config"
}