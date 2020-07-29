package be.technifutur.devmob9.sdsftryone.tools

import java.lang.IllegalArgumentException

class CardEventParam(var card: CardType, var playerId: Int) : EventParam {

    init {
        if (playerId <=0) {
            throw IllegalArgumentException()
        }
    }

    companion object : StringDataCreator<CardEventParam> {
        override fun createFrom(string: String): CardEventParam? {
            val regex = Regex("^([ERY])([1-9]\\d*)$")
            val result = regex.matchEntire(string) ?: return null

            val card = CardType.createFrom(result.groupValues[1])!!
            val playerId = result.groupValues[2].toInt()

            return CardEventParam(card, playerId)
        }
    }

    override fun toString(): String {
        return "${card.jsonString}${playerId}"
    }
}
