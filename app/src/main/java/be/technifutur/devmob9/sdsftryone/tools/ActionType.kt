package be.technifutur.devmob9.sdsftryone.tools

enum class ActionType (val char: Char) {
    ADD('A'), UPDATE('U'), REMOVE('R');

    companion object {
        fun createFrom (char: Char): ActionType? {
            return ActionType.values().firstOrNull { it.char == char }
        }
    }
}