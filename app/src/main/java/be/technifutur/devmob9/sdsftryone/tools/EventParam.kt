package be.technifutur.devmob9.sdsftryone.tools

class EventParam (var primaryPlayer: Int = 0,
                  var secondaryPlayer: Int = 0,
                  var isPenalty: Boolean = false,
                  var carteType: CarteType = CarteType.YELLOW,
                  var isSuccess: Boolean = false,
                  var chronoType: ChronoType = ChronoType.BEGIN
) {
    companion object {


        fun getEventParam (string: String): EventParam? {

            return null

        }
    }

    override fun toString(): String {
        return super.toString()
    }
}



enum class CarteType {
    YELLOW,
    RED
}

enum class ChronoType {
    BEGIN,
    END,
    PAUSE,
    RESUME,
    STOP,
    Cancel
}

// contiendra deux players
// un bouléen si c'est un penalty
// un enume pour le type de carte carteType
// un enum pour  goal/penalty success ou failed --> isSuccess Bool
// un enum pour le chrono 4 val possible
// begin, end, pause, resume, stop  chronoType