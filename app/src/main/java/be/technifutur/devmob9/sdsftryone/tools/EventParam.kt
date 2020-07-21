package be.technifutur.devmob9.sdsftryone.tools

class EventParam (var primaryPlayer: Int,
                  var secondaryPlayer: Int,
                  var isPenalty: Boolean,
                  var carteType: CarteType,
                  var isSuccess: Boolean,
                  var chronoType: ChronoType
)

enum class CarteType {
    YELLOW,
    RED
}

enum class ChronoType {
    BEGIN,
    END,
    PAUSE,
    RESUME,
    STOP
}

// contiendra deux players
// un bouléen si c'est un penalty
// un enume pour le type de carte carteType
// un enum pour  goal/penalty success ou failed --> isSuccess Bool
// un enum pour le chrono 4 val possible
// begin, end, pause, resume, stop  chronoType